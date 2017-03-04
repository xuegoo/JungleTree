package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class BlockActionMessage implements Message {

    private final int x;
    private final int y;
    private final int z;
    private final int actionId;
    private final int actionParam;
    private final int blockType;

    public BlockActionMessage(int x, int y, int z, int actionId, int actionParam, int blockType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.actionId = actionId;
        this.actionParam = actionParam;
        this.blockType = blockType;
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

    public int getActionId() {
        return actionId;
    }

    public int getActionParam() {
        return actionParam;
    }

    public int getBlockType() {
        return blockType;
    }
}
