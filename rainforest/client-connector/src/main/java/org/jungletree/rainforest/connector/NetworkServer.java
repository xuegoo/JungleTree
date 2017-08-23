package org.jungletree.rainforest.connector;

import io.netty.channel.ChannelFuture;

import java.net.SocketAddress;

public interface NetworkServer {

    ChannelFuture bind(SocketAddress address);

    void onBindSuccess(SocketAddress address);

    void onBindFailure(SocketAddress address, Throwable t);

    void shutdown();
}
