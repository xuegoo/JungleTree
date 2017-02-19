package im.octo.jungletree.network;

import com.flowpowered.network.ConnectionManager;
import com.flowpowered.network.session.Session;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.network.NetworkServer;
import im.octo.jungletree.network.pipeline.JChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

@Singleton
public class JNetworkServer implements NetworkServer, ConnectionManager {

    private static final Logger log = LoggerFactory.getLogger(JNetworkServer.class);

    private final EventLoopGroup boss;
    private final EventLoopGroup worker;
    private final ServerBootstrap bootstrap;
    private final Server server;
    private final SessionRegistry sessionRegistry;
    private CountDownLatch latch;
    private Channel channel;

    @Inject
    public JNetworkServer(CountDownLatch latch) {
        this.server = Rainforest.getServer();

        Injector guice = server.getGuice();
        this.sessionRegistry = guice.getInstance(SessionRegistry.class);

        this.latch = latch;

        boss = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        worker = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();

        bootstrap.group(boss, worker)
                .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new JChannelInitializer(this));
    }

    public Channel getChannel() {
        return channel;
    }

    public ChannelFuture bind(InetSocketAddress address) {
        log.info("Binding server to {}...", address.toString());
        ChannelFuture future = bootstrap.bind(address).addListener(f -> {
            if (f.isSuccess()) {
                onBindSuccess(address);
            } else {
                onBindFailure(address, f.cause());
            }
        });
        this.channel = future.channel();
        return future;
    }

    public void onBindSuccess(InetSocketAddress address) {
        server.setIp(address.getHostString());
        server.setPort(address.getPort());
        log.info("Successfully bound server to {}.", address.toString());
        latch.countDown();
    }

    public void onBindFailure(InetSocketAddress address, Throwable t) {
        log.error("Failed to bind server to {}.", address.toString());
        if (t.getMessage().contains("Cannot assign requested address")) {
            log.error("The 'server.ip' in your configuration may not be valid.");
            log.error("Unless you are sure you need it, try removing it.");
            log.error(t.getLocalizedMessage());
        } else if (t.getMessage().contains("Address already in use")) {
            log.error(
                    "The address was already in use. Check that no server is\n" +
                    "already running on that port. If needed, try killing all\n" +
                    "Java processes using Task Manager or similar."
            );
            log.error(t.getLocalizedMessage());
        } else {
            log.error("An unknown bind error has occurred.", t);
        }
        System.exit(1);
    }

    @Override
    public Session newSession(Channel c) {
        JSession session = new JSession(c, this);
        sessionRegistry.add(session);
        return session;
    }

    @Override
    public void sessionInactivated(Session session) {
        sessionRegistry.remove((JSession) session);
    }

    public void shutdown() {
        channel.close();
        bootstrap.childGroup().shutdownGracefully();
        bootstrap.group().shutdownGracefully();
    }
}
