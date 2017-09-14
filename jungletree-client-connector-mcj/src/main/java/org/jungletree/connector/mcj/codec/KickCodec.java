package org.jungletree.connector.mcj.codec;

import com.flowpowered.network.Codec;
import org.jungletree.connector.mcj.message.KickMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.readUTF8;
import static com.flowpowered.network.util.ByteBufUtils.writeUTF8;

public class KickCodec implements Codec<KickMessage> {

    @Override
    public KickMessage decode(ByteBuf buffer) throws IOException {
        return new KickMessage(readUTF8(buffer));
    }

    @Override
    public ByteBuf encode(ByteBuf buf, KickMessage message) throws IOException {
        writeUTF8(buf, message.getText());
        return buf;
    }
}
