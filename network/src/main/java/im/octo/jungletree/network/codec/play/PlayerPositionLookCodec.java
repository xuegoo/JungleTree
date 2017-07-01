package im.octo.jungletree.network.codec.play;

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
        boolean onGround = buffer.readBoolean();

        return new PlayerPositionLookMessage(onGround, x, y, z, yaw, pitch);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, PlayerPositionLookMessage message) throws IOException {
        buf.writeDouble(message.getPlayerX());
        buf.writeDouble(message.getPlayerFeetY());
        buf.writeDouble(message.getPlayerZ());
        buf.writeFloat(message.getYaw());
        buf.writeFloat(message.getPitch());
        buf.writeBoolean(message.isGrounded());
        return buf;
    }
}
