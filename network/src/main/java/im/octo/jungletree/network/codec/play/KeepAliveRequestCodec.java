package im.octo.jungletree.network.codec.play;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.message.play.player.KeepAliveRequestMessage;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class KeepAliveRequestCodec implements Codec<KeepAliveRequestMessage> {

    @Override
    public KeepAliveRequestMessage decode(ByteBuf buffer) throws IOException {
        return new KeepAliveRequestMessage(ByteBufUtils.readVarInt(buffer));
    }

    @Override
    public ByteBuf encode(ByteBuf buf, KeepAliveRequestMessage message) throws IOException {
        ByteBufUtils.writeVarInt(buf, message.getKeepAliveId());
        return buf;
    }
}
