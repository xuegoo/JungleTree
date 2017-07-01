package im.octo.jungletree.network.codec.game;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.message.play.game.PluginMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public final class PluginMessageCodec implements Codec<PluginMessage> {

    @Override
    public PluginMessage decode(ByteBuf buf) throws IOException {
        String channel = ByteBufUtils.readUTF8(buf);
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        return new PluginMessage(channel, data);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, PluginMessage message) throws IOException {
        ByteBufUtils.writeUTF8(buf, message.getChannel());
        buf.writeBytes(message.getMessage());
        return buf;
    }
}
