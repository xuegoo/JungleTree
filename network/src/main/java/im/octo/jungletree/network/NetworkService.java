package im.octo.jungletree.network;

import com.google.inject.Guice;
import com.google.inject.Injector;
import im.octo.jungletree.api.GameVersion;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.rainforest.minecraft.entity.Player;
import scheduler.Scheduler;
import io.netty.channel.epoll.Epoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

public class NetworkService implements Server {

    private static final Logger log = LoggerFactory.getLogger(NetworkService.class);

    private static final GameVersion GAME_VERSION = GameVersion.MC_1_11_2;
    private static final int PORT = 25565;

    private final KeyPair keyPair = SecurityUtils.generateKeyPair();

    private final Injector guice;
    private final Scheduler scheduler;
    private JNetworkServer networkServer;

    private NetworkService() {
        this.guice = Guice.createInjector(new NetworkGuiceModule());
        this.scheduler = guice.getInstance(Scheduler.class);

        Rainforest.setServer(this);
        scheduler.execute(this::logStartMessage);

        bind();
    }

    private void logStartMessage() {
        log.info("==========");
        log.info("JungleTree Client Networking Server");
        log.info("==========");
        log.info("Starting service...");
    }

    private InetSocketAddress getBindAddress() {
        return new InetSocketAddress(PORT);
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

    @Override
    public String getImplementationName() {
        return NetworkService.class.getPackage().getImplementationTitle();
    }

    @Override
    public String getImplementationVersion() {
        return NetworkService.class.getPackage().getImplementationVersion();
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
        return "YATTA";
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
    public void setIp(String host) {

    }

    @Override
    public int getPort() {
        return PORT;
    }

    @Override
    public void setPort(int port) {

    }

    @Override
    public void broadcastMessage(String message) {

    }

    public static void main(String[] args) {
        new NetworkService();
    }
}
