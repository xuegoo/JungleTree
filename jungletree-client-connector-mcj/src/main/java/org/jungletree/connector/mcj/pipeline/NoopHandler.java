package org.jungletree.connector.mcj.pipeline;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;

/**
 * ChannelHandler which does nothing.
 */
@Sharable
public final class NoopHandler extends ChannelHandlerAdapter {

    public static final NoopHandler INSTANCE = new NoopHandler();

    private NoopHandler() {
    }
}
