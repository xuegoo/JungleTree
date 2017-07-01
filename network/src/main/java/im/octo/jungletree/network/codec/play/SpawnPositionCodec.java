package im.octo.jungletree.network.codec.play;

import com.flowpowered.network.Codec;
import im.octo.jungletree.api.util.Vector;
import im.octo.jungletree.network.JByteBufUtils;
import im.octo.jungletree.network.message.play.player.SpawnPositionMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class SpawnPositionCodec implements Codec<SpawnPositionMessage> {

    @Override
    public SpawnPositionMessage decode(ByteBuf buffer) throws IOException {
        Vector pos = JByteBufUtils.readBlockPosition(buffer);
        return new SpawnPositionMessage(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
    }

    @Override
    public ByteBuf encode(ByteBuf buf, SpawnPositionMessage message) throws IOException {
        JByteBufUtils.writeBlockPosition(buf, message.getX(), message.getY(), message.getZ());
        return buf;
    }
}
