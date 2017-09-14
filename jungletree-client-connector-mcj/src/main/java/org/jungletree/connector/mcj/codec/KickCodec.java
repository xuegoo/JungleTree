package org.jungletree.connector.mcj.codec;

import com.flowpowered.network.Codec;
import io.netty.buffer.ByteBuf;
import org.jungletree.connector.mcj.message.KickMessage;

import java.io.IOException;

import static org.jungletree.connector.mcj.ByteBufUtils.readText;
import static org.jungletree.connector.mcj.ByteBufUtils.writeText;

public class KickCodec implements Codec<KickMessage> {

    @Override
    public KickMessage decode(ByteBuf buf) throws IOException {
        return new KickMessage(readText(buf));
    }

    @Override
    public ByteBuf encode(ByteBuf buf, KickMessage message) throws IOException {
        writeText(buf, message.getText());
        return buf;
    }
}
