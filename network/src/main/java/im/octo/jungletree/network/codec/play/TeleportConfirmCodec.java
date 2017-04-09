package im.octo.jungletree.network.codec.play;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.message.play.player.TeleportConfirmMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class TeleportConfirmCodec implements Codec<TeleportConfirmMessage> {

    @Override
    public TeleportConfirmMessage decode(ByteBuf buffer) throws IOException {
        int id = ByteBufUtils.readVarInt(buffer);
        return new TeleportConfirmMessage(id);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, TeleportConfirmMessage message) throws IOException {
        ByteBufUtils.writeVarInt(buf, message.getTeleportId());
        return buf;
    }
}
