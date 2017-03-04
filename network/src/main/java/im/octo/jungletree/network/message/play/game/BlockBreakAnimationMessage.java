package im.octo.jungletree.network.message.play.game;

import com.flowpowered.network.Message;

public class BlockBreakAnimationMessage implements Message {

    private final int entityId;
    private final int x;
    private final int y;
    private final int z;
    private final int destroyStage;

    public BlockBreakAnimationMessage(int entityId, int x, int y, int z, int destroyStage) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.destroyStage = destroyStage;
    }

    public int getEntityId() {
        return entityId;
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

    public int getDestroyStage() {
        return destroyStage;
    }
}
