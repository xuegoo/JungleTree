package im.octo.jungletree.network.codec.play;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.JByteBufUtils;
import im.octo.jungletree.network.message.play.PositionRotationMessage;
import im.octo.jungletree.network.message.play.player.PlayerPositionLookMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class PositionRotationCodec implements Codec<PositionRotationMessage> {

    @Override
    public PositionRotationMessage decode(ByteBuf buffer) throws IOException {
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        float rotation = buffer.readFloat();
        float pitch = buffer.readFloat();
        int flags = buffer.readUnsignedByte();
        int teleportID = ByteBufUtils.readVarInt(buffer);

        return new PositionRotationMessage(x, y, z, rotation, pitch, flags, teleportID);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, PositionRotationMessage message) throws IOException {
        buf.writeDouble(message.getX());
        buf.writeDouble(message.getY());
        buf.writeDouble(message.getZ());
        buf.writeFloat(message.getYaw());
        buf.writeFloat(message.getPitch());
        buf.writeByte(message.getFlags());
        ByteBufUtils.writeVarInt(buf, message.getTeleportId());
        return buf;
    }
}
