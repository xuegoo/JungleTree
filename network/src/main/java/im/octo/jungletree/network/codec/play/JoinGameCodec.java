package im.octo.jungletree.network.codec.play;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;
import im.octo.jungletree.network.message.play.player.JoinGameMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class JoinGameCodec implements Codec<JoinGameMessage> {

    @Override
    public JoinGameMessage decode(ByteBuf buffer) throws IOException {
        int id = buffer.readInt();
        byte gameMode = buffer.readByte();
        int dimension = buffer.readInt();
        byte difficulty = buffer.readByte();
        byte maxPlayers = buffer.readByte();
        String levelType = ByteBufUtils.readUTF8(buffer);
        boolean reducedDebug = buffer.readBoolean();
        return new JoinGameMessage(id, gameMode, dimension, difficulty, maxPlayers, levelType, reducedDebug);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, JoinGameMessage message) throws IOException {
        buf.writeInt(message.getId());
        buf.writeByte(message.getMode());
        buf.writeInt(message.getDimension());
        buf.writeByte(message.getDifficulty());
        buf.writeByte(message.getMaxPlayers());
        ByteBufUtils.writeUTF8(buf, message.getLevelType());
        buf.writeBoolean(message.isReducedDebugInfo());
        return buf;
    }
}
