package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class SpawnGlobalEntityMessage implements Message {

    private final int entityId;
    private final int type;
    private final double x;
    private final double y;
    private final double z;

    public SpawnGlobalEntityMessage(int entityId, int type, double x, double y, double z) {
        this.entityId = entityId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
