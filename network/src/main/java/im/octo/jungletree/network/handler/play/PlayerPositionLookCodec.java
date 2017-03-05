package im.octo.jungletree.network.handler.play;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.message.play.player.PlayerPositionLookMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class PlayerPositionLookCodec implements Codec<PlayerPositionLookMessage> {

    @Override
    public PlayerPositionLookMessage decode(ByteBuf buffer) throws IOException {
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        float yaw = buffer.readFloat();
        float pitch = buffer.readFloat();
        int flags = buffer.readUnsignedByte();
        int teleportID = ByteBufUtils.readVarInt(buffer);
        return new PlayerPositionLookMessage(x, y, z, yaw, pitch, flags, teleportID);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, PlayerPositionLookMessage message) throws IOException {
        buf.writeDouble(message.getPlayerX());
        buf.writeDouble(message.getPlayerFeetY());
        buf.writeDouble(message.getPlayerZ());
        buf.writeFloat(message.getYaw());
        buf.writeFloat(message.getPitch());
        buf.writeByte(message.getFlags());
        ByteBufUtils.writeVarInt(buf, message.getTeleportId());
        return buf;
    }
}
