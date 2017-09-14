package org.jungletree.connector.mcj;

import com.flowpowered.network.ConnectionManager;
import com.flowpowered.network.session.Session;
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
import org.jungletree.connector.mcj.pipeline.JChannelInitializer;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.connector.ClientConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

public class JungleClientConnectorMcj implements ClientConnector, ConnectionManager {

    private static final Logger log = LoggerFactory.getLogger(JungleClientConnectorMcj.class);

    private final ClientConnectorResourceService resource;
    private final CountDownLatch latch;

    private final EventLoopGroup boss;
    private final EventLoopGroup worker;
    private final ServerBootstrap bootstrap;

    private Channel channel;
    private String host;
    private int port;

    @Inject
    public JungleClientConnectorMcj(JChannelInitializer channelInitializer, CountDownLatch latch, ClientConnectorResourceService resource) {
        this.latch = latch;
        this.resource = resource;

        boolean epoll = Epoll.isAvailable();

        this.boss = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        this.worker = epoll ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        this.bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(epoll ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(channelInitializer);
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public ChannelFuture bind(InetSocketAddress address) {
        ChannelFuture channelFuture = bootstrap.bind(address)
                .addListener(f -> {
                    if (f.isSuccess()) {
                        onBindSuccess(address);
                    } else {
                        onBindFailure(address, f.cause());
                    }
                });
        this.channel = channelFuture.channel();
        return channelFuture;
    }

    @Override
    public void onBindSuccess(InetSocketAddress address) {
        this.host = address.getHostString();
        this.port = address.getPort();
        log.info("Client connector service bound on {}:{}", host, port);
    }

    @Override
    public void onBindFailure(InetSocketAddress address, Throwable t) {
        log.error("Failed to bind client connector server on {}:{}", address.getHostString(), address.getPort(), t);
        System.exit(1);
    }

    @Override
    public Session newSession(Channel c) {
        JSession session = new JSession(c, resource, this);

        return session;
    }

    @Override
    public void sessionInactivated(Session session) {

    }

    @Override
    public void shutdown() {
        channel.close();
        bootstrap.config().childGroup().shutdownGracefully();
        bootstrap.config().group().shutdownGracefully();
    }
}
