package org.jungletree.connector.mcj.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.jungletree.rainforest.connector.MessageProcessor;

import javax.inject.Inject;
import java.util.List;

public class MessageProcessorDecoder extends ByteToMessageDecoder {

    private final MessageHandler messageHandler;

    @Inject
    public MessageProcessorDecoder(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> frames) throws Exception {
        MessageProcessor processor = messageHandler.getSession().getProcessor();
        if (processor == null) {
            frames.add(in.readBytes(in.readableBytes()));
            return;
        }
        // Eventually, we will run out of bytes and a ReplayableError will be called
        ByteBuf liveBuffer = ctx.alloc().buffer();
        liveBuffer = processor.processInbound(ctx, in, liveBuffer);
        frames.add(liveBuffer);
    }
}
