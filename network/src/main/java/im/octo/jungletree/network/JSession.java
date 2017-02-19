package im.octo.jungletree.network;

import com.flowpowered.network.AsyncableMessage;
import com.flowpowered.network.ConnectionManager;
import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.flowpowered.network.exception.ChannelClosedException;
import com.flowpowered.network.session.BasicSession;
import com.google.inject.Injector;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.type.player.PlayerJoinEvent;
import im.octo.jungletree.api.event.type.player.PlayerKickEvent;
import im.octo.jungletree.api.event.type.player.PlayerLoginEvent;
import im.octo.jungletree.api.exception.IllegalOperationException;
import im.octo.jungletree.api.network.LoginResult;
import im.octo.jungletree.api.player.PlayerDataService;
import im.octo.jungletree.api.player.PlayerDataService.PlayerReader;
import im.octo.jungletree.api.player.PlayerSession;
import im.octo.jungletree.api.player.meta.PlayerProfile;
import im.octo.jungletree.network.message.KickMessage;
import im.octo.jungletree.network.message.SetCompressionMessage;
import im.octo.jungletree.network.message.login.LoginSuccessMessage;
import im.octo.jungletree.network.pipeline.CodecsHandler;
import im.octo.jungletree.network.pipeline.CompressionHandler;
import im.octo.jungletree.network.pipeline.EncryptionHandler;
import im.octo.jungletree.network.protocol.JProtocol;
import im.octo.jungletree.network.protocol.LoginProtocol;
import im.octo.jungletree.network.protocol.ProtocolType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.CodecException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class JSession extends BasicSession implements PlayerSession {

    private static final Logger log = LoggerFactory.getLogger(JSession.class);

    private final Server server;
    private final Random random = ThreadLocalRandom.current();
    private final Queue<Message> messageQueue = new ArrayDeque<>();

    private ConnectionManager connectionManager;
    private InetSocketAddress address;
    private boolean online;
    private byte[] verifyToken;
    private String verifyUsername;
    private String quitReason;
    private String hostname;
    private int version = -1;
    private ProxyData proxyData;
    private Player player;
    private int pingMessageId;
    private boolean disconnected;
    private boolean compressionSent;

    // private BlockPlacementMessage previousPlacement;
    // private int previousPlacementTicks;

    public JSession(Channel channel, ConnectionManager connectionManager) {
        super(channel, ProtocolType.HANDSHAKE.getProtocol());
        this.server = Rainforest.getServer();
        this.connectionManager = connectionManager;
        this.address = super.getAddress();
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(byte... verifyToken) {
        this.verifyToken = verifyToken;
    }

    public String getVerifyUsername() {
        return verifyUsername;
    }

    public void setVerifyUsername(String username) {
        this.verifyUsername = username;
    }

    public ProxyData getProxyData() {
        return proxyData;
    }

    public void setProxyData(ProxyData proxyData) {
        this.proxyData = proxyData;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void idle() {
        // TODO: Implement
        // if (pingMessageId == 0 && getProtocol() instanceof PlayProtocol) {
        //}
    }

    public void pong(long pingId) {
        if (pingId == pingMessageId) {
            pingMessageId = 0;
        }
    }

    @Override
    public InetSocketAddress getAddress() {
        return address;
    }

    public boolean isOnline() {
        return online;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(PlayerProfile profile) {
        if (player != null) {
            throw new IllegalOperationException("Cannot set player twice");
        }

        if (!isActive()) {
            return;
        }

        Injector guice = server.getGuice();
        PlayerDataService playerDataService = guice.getInstance(PlayerDataService.class);
        PlayerReader reader = playerDataService.beginReadingData(profile.getUuid());
        player = playerDataService.initializePlayer(this, profile, reader);
        finalizeLogin(profile);

        if (!isActive()) {
            reader.close();
            onDisconnect();
            return;
        }

        for (Player other : server.getOnlinePlayers()) {
            if (other != player && other.getUuid().equals(player.getUuid())) {
                PlayerSession playerSession = other.getSession();
                if (playerSession instanceof JSession) {
                    JSession session = (JSession)playerSession;
                    session.disconnect("You logged in from another location.", true);
                    break;
                } else {
                    log.error("PlayerSession is not an instance of JSession. You must be using a different implementation.");
                }
            }
        }

        PlayerLoginEvent event = new PlayerLoginEvent(player, address.getAddress(), hostname);
        EventService eventService = guice.getInstance(EventService.class);
        eventService.call(event);

        if (!event.getLoginResult().equals(LoginResult.ALLOWED)) {
            disconnect(event.getKickMessage(), true);
            return;
        }

        playerDataService.join(this, reader, player);
        player.getWorld().getPlayers().add(player);
        online = true;
        log.info("{} [{}] connected, UUID: {}", player.getName(), address, player.getUuid());

        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(player);
        eventService.call(playerJoinEvent);

        String message = playerJoinEvent.getJoinMessage();
        if (message != null && !message.isEmpty()) {
            server.broadcastMessage(message);
        }

        // TODO: User list
        // Message addMessage = new UserListItemMessage(Action)
    }

    @Override
    public ChannelFuture sendWithFuture(Message message) throws ChannelClosedException {
        if (!isActive()) {
            return null;
        }
        return super.sendWithFuture(message);
    }

    @Deprecated
    @Override
    public void disconnect() {
        disconnect("No reason specified.");
    }

    public void disconnect(String reason) {
        disconnect(reason, false);
    }

    public void disconnect(String reason, boolean overrideKick) {
        if (player != null && !overrideKick) {
            PlayerKickEvent event = new PlayerKickEvent(player, reason);
            Injector guice = server.getGuice();
            EventService eventService = guice.getInstance(EventService.class);
            eventService.call(event);

            if (event.isCancelled()) {
                return;
            }
            reason = event.getKickReason();

            if (player.isOnline() && event.getLeaveMessage() != null) {
                server.broadcastMessage(event.getLeaveMessage());
            }
        }

        if (player != null) {
            log.info("{} kicked: {}", player.getName(), reason);
        } else {
            log.info("[{}] kicked: {}", reason);
        }

        if (quitReason == null) {
            quitReason = "kicked";
        }

        if (isActive() && (getProtocol() instanceof LoginProtocol /* || getProtocol() instanceof PlayProtocol*/)) {
            sendWithFuture(new KickMessage(reason))
                    .addListener(ChannelFutureListener.CLOSE);
        } else {
            getChannel().close();
        }
    }

    void pulse() {
        // TODO: Implement
    }

    private void finalizeLogin(PlayerProfile profile) {
        int compression = server.getCompressionThreshold();
        if (compression > 0) {
            enableCompression(compression);
        }

        send(new LoginSuccessMessage(profile.getUuid().toString(), profile.getUsername()));
        // setProtocol(ProtocolType.PLAY);
    }

    public void setProtocol(ProtocolType protocolType) {
        getChannel().flush();

        JProtocol protocol = protocolType.getProtocol();
        updatePipeline("codecs", new CodecsHandler(protocol));
        setProtocol(protocol);
    }

    public void enableEncryption(SecretKey sharedSecret) {
        updatePipeline("encryption", new EncryptionHandler(sharedSecret));
    }

    public void enableCompression(int threshold) {
        if (!compressionSent) {
            send(new SetCompressionMessage(threshold));
            updatePipeline("compression", new CompressionHandler(threshold));
            compressionSent = true;
        }
    }

    private void updatePipeline(String key, ChannelHandler handler) {
        getChannel().pipeline().replace(key, key, handler);
    }

    @Override
    public void onDisconnect() {
        disconnected = true;
    }

    @Override
    public void messageReceived(Message message) {
        if (message instanceof AsyncableMessage && ((AsyncableMessage)message).isAsync()) {
            super.messageReceived(message);
        } else {
            messageQueue.add(message);
        }
    }

    @Override
    public void onInboundThrowable(Throwable throwable) {
        if (throwable instanceof CodecException) {
            log.error("Error in network input", throwable);
        } else {
            if (quitReason == null) {
                quitReason = "Read error: " + throwable;
            }
            getChannel().close();
        }
    }

    @Override
    public void onOutboundThrowable(Throwable throwable) {
        if (throwable instanceof CodecException) {
            log.error("Error in network output", throwable);
        } else {
            if (quitReason == null) {
                quitReason = "Write error: " + throwable;
            }
            getChannel().close();
        }
    }

    @Override
    public void onHandlerThrowable(Message message, MessageHandler<?, ?> handle, Throwable throwable) {
        log.error("Error whilst handling {} (handler: {})", message, handle.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        if (player != null) {
            return player.getName() + "[" + address + "]";
        } else {
            return "[" + address + "]";
        }
    }
}
