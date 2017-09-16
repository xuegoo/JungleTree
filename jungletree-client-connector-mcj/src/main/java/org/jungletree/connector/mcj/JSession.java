package org.jungletree.connector.mcj;

import com.flowpowered.network.AsyncableMessage;
import com.flowpowered.network.ConnectionManager;
import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.flowpowered.network.exception.ChannelClosedException;
import com.flowpowered.network.session.BasicSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.CodecException;
import org.jungletree.connector.mcj.handler.login.model.PlayerProfile;
import org.jungletree.connector.mcj.message.KickMessage;
import org.jungletree.connector.mcj.message.SetCompressionMessage;
import org.jungletree.connector.mcj.message.login.LoginSuccessMessage;
import org.jungletree.connector.mcj.pipeline.CodecsHandler;
import org.jungletree.connector.mcj.pipeline.CompressionHandler;
import org.jungletree.connector.mcj.pipeline.EncryptionHandler;
import org.jungletree.connector.mcj.player.JunglePlayer;
import org.jungletree.connector.mcj.protocol.JProtocol;
import org.jungletree.connector.mcj.protocol.LoginProtocol;
import org.jungletree.connector.mcj.protocol.PlayProtocol;
import org.jungletree.connector.mcj.protocol.ProtocolType;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.util.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.net.InetSocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;

public class JSession extends BasicSession {

    private static final Logger log = LoggerFactory.getLogger(JSession.class);

    private final Queue<Message> messageQueue = new ArrayDeque<>();

    private final ClientConnectorResourceService resource;
    private final MessagingService messaging;

    private ConnectionManager connectionManager;
    private InetSocketAddress address;
    private boolean online;
    private byte[] verifyToken;
    private String verifyUsername;
    private String quitReason;
    private String hostname;
    private int version = -1;
    private int pingMessageId;
    private boolean disconnected;
    private boolean compressionSent;

    private JunglePlayer player;

    // private BlockPlacementMessage previousPlacement;
    // private int previousPlacementTicks;

    public JSession(Channel channel, ClientConnectorResourceService resource, MessagingService messaging, ConnectionManager connectionManager) {
        super(channel, ProtocolType.HANDSHAKE.getProtocol());
        this.resource = resource;
        this.messaging = messaging;
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

    public void disconnect(Text reason) {
        disconnect(reason, false);
    }

    public void disconnect(String reason) {
        disconnect(reason, false);
    }

    public void disconnect(String reason, boolean overrideKick) {
        if (player != null) {
            log.info("{} kicked. Reason: {}", player.getName(), reason);
        }

        if (isActive() && (getProtocol() instanceof LoginProtocol || getProtocol() instanceof PlayProtocol)) {
            sendWithFuture(new KickMessage(reason)).addListener(ChannelFutureListener.CLOSE);
        } else {
            getChannel().close();
        }
    }

    public void disconnect(Text reason, boolean overrideKick) {
        if (player != null) {
            log.info("{} kicked. Reason: {}", player.getName(), reason);
        }

        if (isActive() && (getProtocol() instanceof LoginProtocol || getProtocol() instanceof PlayProtocol)) {
            sendWithFuture(new KickMessage(reason)).addListener(ChannelFutureListener.CLOSE);
        } else {
            getChannel().close();
        }
    }

    void pulse() {
        // TODO: Implement
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

    public void setPlayer(PlayerProfile profile) {
        if (player != null) {
            throw new IllegalStateException("Player already defined");
        }

        this.player = new JunglePlayer(messaging, resource, profile);
        finalizeLogin(player, profile);

        if (!isActive()) {
            onDisconnect();
            return;
        }

        log.info("{} [{}] connected, UUID: {}", player.getName(), address, player.getUniqueId());
    }

    private void finalizeLogin(JunglePlayer player, PlayerProfile profile) {
        int threshold = resource.getCompressionThreshold();
        if (threshold > 0) {
            enableCompression(threshold);
        }

        send(new LoginSuccessMessage(profile.getUniqueId().toString(), profile.getName()));
        setProtocol(ProtocolType.PLAY);
        player.join(this);
    }

    private void updatePipeline(String key, ChannelHandler handler) {
        getChannel().pipeline().replace(key, key, handler);
    }

    @Override
    public void onDisconnect() {
        disconnected = true;
        if (player != null) {
            player.quit();
        }
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
        throwable.printStackTrace();
    }
}
