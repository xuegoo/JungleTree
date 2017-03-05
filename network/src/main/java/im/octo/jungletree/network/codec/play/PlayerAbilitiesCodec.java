package im.octo.jungletree.network.codec.play;

import com.flowpowered.network.Codec;
import im.octo.jungletree.network.message.play.player.PlayerAbilitiesMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class PlayerAbilitiesCodec implements Codec<PlayerAbilitiesMessage> {

    @Override
    public PlayerAbilitiesMessage decode(ByteBuf buf) throws IOException {
        int flags = buf.readUnsignedByte();
        float flySpeed = buf.readFloat();
        float walkSpeed = buf.readFloat();
        return new PlayerAbilitiesMessage(flags, flySpeed, walkSpeed);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, PlayerAbilitiesMessage message) throws IOException {
        buf.writeByte(message.getFlags());
        buf.writeFloat(message.getFlyingSpeed());
        buf.writeFloat(message.getWalkingSpeed());
        return buf;
    }
}
