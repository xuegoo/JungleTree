package im.octo.jungletree.network.codec;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.message.SetCompressionMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.writeVarInt;

public class SetCompressionCodec implements Codec<SetCompressionMessage> {

    @Override
    public SetCompressionMessage decode(ByteBuf buffer) throws IOException {
        int threshold = ByteBufUtils.readVarInt(buffer);
        return new SetCompressionMessage(threshold);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, SetCompressionMessage message) throws IOException {
        writeVarInt(buf, message.getThreshold());
        return buf;
    }
}
