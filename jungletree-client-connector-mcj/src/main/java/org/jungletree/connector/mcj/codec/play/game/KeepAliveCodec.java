package org.jungletree.connector.mcj.codec.play.game;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import org.jungletree.connector.mcj.message.play.game.KeepAliveMessage;

import java.io.IOException;

public class KeepAliveCodec implements Codec<KeepAliveMessage> {

    @Override
    public KeepAliveMessage decode(ByteBuf buf) throws IOException {
        return new KeepAliveMessage(ByteBufUtils.readVarInt(buf));
    }

    @Override
    public ByteBuf encode(ByteBuf buf, KeepAliveMessage message) throws IOException {
        ByteBufUtils.writeVarInt(buf, message.getPingId());
        return buf;
    }
}
