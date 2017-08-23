package org.jungletree.rainforest.connector;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface MessageProcessor {

    ByteBuf processOutbound(ChannelHandlerContext ctx, ByteBuf input, ByteBuf buffer);

    ByteBuf processInbound(ChannelHandlerContext ctx, ByteBuf input, ByteBuf buffer);
}
