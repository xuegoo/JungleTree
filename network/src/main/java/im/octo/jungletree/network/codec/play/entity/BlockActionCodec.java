package im.octo.jungletree.network.codec.play.entity;

import com.flowpowered.network.Codec;
import im.octo.jungletree.api.util.Vector;
import im.octo.jungletree.network.JByteBufUtils;
import im.octo.jungletree.network.message.play.entity.BlockActionMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.readVarInt;
import static com.flowpowered.network.util.ByteBufUtils.writeVarInt;

public class BlockActionCodec implements Codec<BlockActionMessage> {

    @Override
    public BlockActionMessage decode(ByteBuf buf) throws IOException {
        Vector position = JByteBufUtils.readBlockPosition(buf);
        int actionId = buf.readUnsignedByte();
        int actionParam = buf.readUnsignedByte();
        int blockType = readVarInt(buf);
        return new BlockActionMessage(position, actionId, actionParam, blockType);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, BlockActionMessage message) throws IOException {
        JByteBufUtils.writeBlockPosition(buf, message.getLocation());
        buf.writeByte(message.getActionId());
        buf.writeByte(message.getActionParam());
        writeVarInt(buf, message.getBlockType());
        return buf;
    }
}
