package org.jungletree.connector.mcj.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.jungletree.rainforest.connector.CodecRegistration;
import org.jungletree.rainforest.connector.Message;
import org.jungletree.rainforest.connector.Protocol;

import javax.inject.Inject;
import java.util.List;

public class MessageEncoder extends MessageToMessageEncoder<Message> {

    private final MessageHandler messageHandler;

    @Inject
    public MessageEncoder(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        Protocol protocol = messageHandler.getSession().getProtocol();
        Class<? extends Message> clazz = msg.getClass();
        CodecRegistration reg = protocol.getCodecRegistration(msg.getClass());

        if (reg == null) {
            throw new Exception("Unknown message type: " + clazz + ".");
        }

        ByteBuf messageBuf = ctx.alloc().buffer();
        messageBuf = reg.getCodec().encode(messageBuf, msg);

        ByteBuf headerBuf = ctx.alloc().buffer();
        headerBuf = protocol.writeHeader(headerBuf, reg, messageBuf);
        out.add(Unpooled.wrappedBuffer(headerBuf, messageBuf));
    }
}
