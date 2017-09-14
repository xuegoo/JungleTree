package org.jungletree.rainforest.connector;

import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;

public interface ClientConnector {

    ChannelFuture bind(InetSocketAddress address);

    default void onBindSuccess(InetSocketAddress address) {
    }

    default void onBindFailure(InetSocketAddress address, Throwable t) {
    }

    void shutdown();
}
