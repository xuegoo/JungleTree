package org.jungletree.connector.mcj.codec.play.game;

import com.flowpowered.network.Codec;
import io.netty.buffer.ByteBuf;
import org.jungletree.connector.mcj.message.play.game.JoinGameMessage;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.readUTF8;
import static com.flowpowered.network.util.ByteBufUtils.writeUTF8;

public class JoinGameCodec implements Codec<JoinGameMessage> {

    @Override
    public JoinGameMessage decode(ByteBuf buf) throws IOException {
        int id = buf.readInt();
        byte gameMode = buf.readByte();
        int dimension = buf.readInt();
        byte difficulty = buf.readByte();
        byte maxPlayers = buf.readByte();
        String levelType = readUTF8(buf);
        boolean reducedDebug = buf.readBoolean();

        return new JoinGameMessage(id, gameMode, dimension, difficulty, maxPlayers, levelType, reducedDebug);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, JoinGameMessage message) throws IOException {
        buf.writeInt(message.getId());
        buf.writeByte(message.getMode());
        buf.writeInt(message.getDimension());
        buf.writeByte(message.getDifficulty());
        buf.writeByte(message.getMaxPlayers());
        writeUTF8(buf, message.getLevelType());
        buf.writeBoolean(message.isReducedDebugInfo());
        return buf;
    }
}
