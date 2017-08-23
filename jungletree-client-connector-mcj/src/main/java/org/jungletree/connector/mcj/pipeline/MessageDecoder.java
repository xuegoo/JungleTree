package org.jungletree.connector.mcj.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.jungletree.rainforest.connector.Codec;
import org.jungletree.rainforest.connector.Message;
import org.jungletree.rainforest.connector.Protocol;
import org.jungletree.rainforest.connector.exception.UnknownPacketException;

import javax.inject.Inject;
import java.util.List;

public class MessageDecoder extends ReplayingDecoder<ByteBuf> {

    private MessageHandler messageHandler;

    @Inject
    public MessageDecoder(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Protocol protocol = messageHandler.getSession().getProtocol();
        Codec<?> codec;
        try {
            codec = protocol.readHeader(in);
        } catch (UnknownPacketException e) {
            // We want to catch this and read the length if possible
            int length = e.getLength();
            if (length != -1 && length != 0) {
                in.readBytes(length);
            }
            throw e;
        }

        if (codec == null) {
            throw new UnsupportedOperationException("Protocol#readHeader cannot return null!");
        }
        Message decoded = codec.decode(in);
        out.add(decoded);
    }
}
