package org.jungletree.connector.mcj.pipeline;

import com.flowpowered.network.Codec;
import com.flowpowered.network.Message;
import org.jungletree.connector.mcj.protocol.JProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.flowpowered.network.Codec.CodecRegistration;
import static com.flowpowered.network.util.ByteBufUtils.writeVarInt;

public class CodecsHandler extends MessageToMessageCodec<ByteBuf, Message> {

    private static final Logger log = LoggerFactory.getLogger(CodecsHandler.class);

    private final JProtocol protocol;

    public CodecsHandler(JProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        Class<? extends Message> clazz = msg.getClass();
        CodecRegistration reg = protocol.getCodecRegistration(clazz);
        if (reg == null) {
            throw new EncoderException("Unknown message type: " + clazz);
        }

        // Header
        ByteBuf headerBuf = ctx.alloc().buffer(8);
        writeVarInt(headerBuf, reg.getOpcode());

        // Body
        ByteBuf messageBuf = ctx.alloc().buffer();
        messageBuf = reg.getCodec().encode(messageBuf, msg);

        out.add(Unpooled.wrappedBuffer(headerBuf, messageBuf));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        // Header
        Codec<?> codec = protocol.readHeader(msg);

        // Body
        Message decoded = codec.decode(msg);
        if (msg.readableBytes() > 0) {
            log.warn("Leftover bytes ({}) after decoding: {}", msg.readableBytes(), decoded);
        }
        out.add(decoded);
    }
}
