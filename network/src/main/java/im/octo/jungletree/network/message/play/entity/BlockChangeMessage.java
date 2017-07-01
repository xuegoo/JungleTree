package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class BlockChangeMessage implements Message {

    private final int x;
    private final int y;
    private final int z;
    private final int blockId;

    public BlockChangeMessage(int x, int y, int z, int blockId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockId = blockId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getBlockId() {
        return blockId;
    }
}
