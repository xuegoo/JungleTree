package im.octo.jungletree.network.message.play.game;

import com.flowpowered.network.Message;
import im.octo.jungletree.network.message.play.entity.BlockChangeMessage;

import java.util.List;

public class MultiBlockChangeMessage implements Message {

    private final int chunkX;
    private final int chunkZ;
    private final List<BlockChangeMessage> records;

    public MultiBlockChangeMessage(int chunkX, int chunkZ, List<BlockChangeMessage> records) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.records = records;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public List<BlockChangeMessage> getRecords() {
        return records;
    }
}
