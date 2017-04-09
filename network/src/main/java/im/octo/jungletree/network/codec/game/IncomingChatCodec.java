package im.octo.jungletree.network.codec.game;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.message.play.player.IncomingChatMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class IncomingChatCodec implements Codec<IncomingChatMessage> {

    @Override
    public IncomingChatMessage decode(ByteBuf buffer) throws IOException {
        return new IncomingChatMessage(ByteBufUtils.readUTF8(buffer));
    }

    @Override
    public ByteBuf encode(ByteBuf buf, IncomingChatMessage message) throws IOException {
        ByteBufUtils.writeUTF8(buf, message.getMessage());
        return buf;
    }
}
