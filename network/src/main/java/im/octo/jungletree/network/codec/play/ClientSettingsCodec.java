package im.octo.jungletree.network.codec.play;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.message.play.player.ClientSettingsMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class ClientSettingsCodec implements Codec<ClientSettingsMessage> {

    @Override
    public ClientSettingsMessage decode(ByteBuf buf) throws IOException {
        String locale = ByteBufUtils.readUTF8(buf);
        int viewDistance = buf.readByte();
        int chatFlags = ByteBufUtils.readVarInt(buf);
        boolean colors = buf.readBoolean();
        int skinFlags = buf.readUnsignedByte();
        int hand = ByteBufUtils.readVarInt(buf);
        return new ClientSettingsMessage(locale, viewDistance, chatFlags, colors, skinFlags, hand);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, ClientSettingsMessage message) throws IOException {
        ByteBufUtils.writeUTF8(buf, message.getLocale());
        buf.writeByte(message.getRenderDistance());
        ByteBufUtils.writeVarInt(buf, message.getChatMode());
        buf.writeBoolean(message.isChatColors());
        buf.writeByte(message.getSkinFlags());
        ByteBufUtils.writeVarInt(buf, message.getMainHand());
        return buf;
    }
}
