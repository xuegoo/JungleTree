package org.jungletree.connector.mcj;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.jungletree.rainforest.connector.NetworkServer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.SocketAddress;

@Singleton
public class JungleNetworkServer implements NetworkServer {

    @Inject
    private BasicChannelInitializer channelInitializer;

    private final ServerBootstrap bootstrap;
    private final EventLoopGroup boss;
    private final EventLoopGroup worker;

    public JungleNetworkServer() {
        this.bootstrap = new ServerBootstrap();
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();

        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public ChannelFuture bind(SocketAddress address) {
        return bootstrap.bind(address)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        onBindSuccess(address);
                    } else {
                        onBindFailure(address, future.cause());
                    }
                });
    }

    @Override
    public void onBindSuccess(SocketAddress address) {
    }

    @Override
    public void onBindFailure(SocketAddress address, Throwable t) {
    }

    @Override
    public void shutdown() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }
}
