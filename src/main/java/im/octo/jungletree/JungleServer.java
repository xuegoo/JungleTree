package im.octo.jungletree;

import com.google.inject.Guice;
import com.google.inject.Injector;
import im.octo.jungletree.api.GameVersion;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.scheduler.TaskScheduler;
import im.octo.jungletree.network.JNetworkServer;
import im.octo.jungletree.network.SecurityUtils;
import io.netty.channel.epoll.Epoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

public class JungleServer implements Server {

    private static final Logger log = LoggerFactory.getLogger(JungleServer.class);

    private static final GameVersion GAME_VERSION = GameVersion.MC_1_11_2;

    private final Injector guice;
    private final TaskScheduler scheduler;
    private JNetworkServer networkServer;

    private int port = 25565;

    private KeyPair keyPair = SecurityUtils.generateKeyPair();
    private String host;

    private JungleServer() {
        this.guice = Guice.createInjector(new JungleGuiceModule());
        this.scheduler = guice.getInstance(TaskScheduler.class);
        Rainforest.setServer(this);

        scheduler.execute(this::logStartMessage);
        // scheduler.shutdown();
    }

    private void logStartMessage() {
        log.info("==========");
        log.info("{} Server running version {}", getImplementationName(), getImplementationVersion());
        log.info("Implementing {} API version {}", getApiName(), getApiVersion());
        log.info("==========");
        log.info("Starting server...");
    }

    public static void main(String[] args) {
        new JungleServer().run();
    }

    @Override
    public String getImplementationName() {
        return JungleServer.class.getPackage().getImplementationTitle();
    }

    @Override
    public String getImplementationVersion() {
        return JungleServer.class.getPackage().getImplementationVersion();
    }

    @Override
    public GameVersion getGameVersion() {
        return GAME_VERSION;
    }

    @Override
    public byte[] getFavicon() {
        return new byte[0];
    }

    @Override
    public String getDescription() {
        return "YATTA!";
    }

    @Override
    public Collection<Player> getOnlinePlayers() {
        return Collections.emptySet();
    }

    @Override
    public int getMaxOnlinePlayers() {
        return 10000;
    }

    @Override
    public int getServerListSampleSize() {
        return 5;
    }

    @Override
    public int getCompressionThreshold() {
        return 0;
    }

    @Override
    public Injector getGuice() {
        return guice;
    }

    @Override
    public KeyPair getKeyPair() {
        return keyPair;
    }

    @Override
    public boolean isOnlineMode() {
        return true;
    }

    @Override
    public void setIp(String host) {
        this.host = host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void broadcastMessage(String message) {
        log.info(message);
    }

    @Override
    public boolean getProxySupport() {
        return false;
    }

    // TODO: Configuration
    private InetSocketAddress getBindAddress() {
        return new InetSocketAddress(getPort());
    }

    private void bind() {
        if (Epoll.isAvailable()) {
            log.info("Native epoll transport is enabled.");
        }

        CountDownLatch latch = new CountDownLatch(3);
        networkServer = new JNetworkServer(latch);
        // TODO: Configuration
        networkServer.bind(getBindAddress());

        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException ex) {
            log.error("Bind interrupted!", ex);
            System.exit(1);
        }
    }

    private void run() {
        bind();
    }
}
