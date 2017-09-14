package org.jungletree.connector.mcj;

import com.google.inject.Guice;
import com.google.inject.Inject;
import io.netty.channel.epoll.Epoll;
import org.jungletree.connector.mcj.config.JClientConnectorResourceService;
import org.jungletree.connector.mcj.protocol.*;
import org.jungletree.rainforest.connector.ClientConnector;
import org.jungletree.rainforest.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

public class JungleMcjApplication {

    private static final Logger log = LoggerFactory.getLogger(JungleMcjApplication.class);

    @Inject
    private MessagingService messaging;

    @Inject
    private JClientConnectorResourceService resource;

    @Inject
    private ClientConnector clientConnector;

    @Inject
    private CountDownLatch latch;

    @Inject private HandshakeProtocol handshakeProtocol;
    @Inject private StatusProtocol statusProtocol;
    @Inject private LoginProtocol loginProtocol;
    @Inject private PlayProtocol playProtocol;

    private JungleMcjApplication() {
        long start = System.currentTimeMillis();
        log.info("Starting JungleTree Minecraft Java Edition connector");

        log.trace("Injecting dependencies");
        Guice.createInjector(new JungleMcjGuiceModule()).injectMembers(this);

        log.trace("Starting messaging service");
        messaging.start();

        log.trace("Initializing protocol");
        initProtocol();

        log.trace("Binding Minecraft Java (MCJ) Server to port {}", resource.getPort());
        bind();

        log.info("Done {}ms", (System.currentTimeMillis() - start));
    }

    private void bind() {
        if (Epoll.isAvailable()) {
            log.info("Native epoll transport is enabled.");
        }

        // TODO: Configuration
        clientConnector.bind(getBindAddress());

        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException ex) {
            log.error("Bind interrupted!", ex);
            System.exit(1);
        }
    }

    private InetSocketAddress getBindAddress() {
        return new InetSocketAddress(resource.getPort());
    }

    private void initProtocol() {
        ProtocolType.HANDSHAKE.setProtocol(handshakeProtocol);
        ProtocolType.STATUS.setProtocol(statusProtocol);
        ProtocolType.LOGIN.setProtocol(loginProtocol);
        ProtocolType.PLAY.setProtocol(playProtocol);
    }

    public static void main(String[] args) {
        new JungleMcjApplication();
    }
}
