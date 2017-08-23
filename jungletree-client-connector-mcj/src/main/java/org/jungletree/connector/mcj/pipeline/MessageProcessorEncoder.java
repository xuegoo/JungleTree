package org.jungletree.connector.mcj.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jungletree.rainforest.connector.MessageProcessor;

import javax.inject.Inject;
import java.util.List;

public class MessageProcessorEncoder extends MessageToMessageEncoder<ByteBuf> {

    private final MessageHandler messageHandler;

    @Inject
    public MessageProcessorEncoder(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        MessageProcessor processor = messageHandler.getSession().getProcessor();
        if (processor == null) {
            out.add(msg.readBytes(msg.readableBytes()));
            return;
        }
        ByteBuf toAdd = ctx.alloc().buffer();
        toAdd = processor.processOutbound(ctx, msg, toAdd);
        out.add(toAdd);
    }
}
