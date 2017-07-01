package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class UpdateBlockEntityMessage implements Message {

    private final int x;
    private final int y;
    private final int z;
    private final int actionId;
    // TODO: NBT
    // private final NbtTag tag;

    public UpdateBlockEntityMessage(int x, int y, int z, int actionId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.actionId = actionId;
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
}
