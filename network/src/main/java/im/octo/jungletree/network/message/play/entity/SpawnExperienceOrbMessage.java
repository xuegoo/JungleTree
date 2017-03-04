package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class SpawnExperienceOrbMessage implements Message {

    private final int entityId;
    private final double x;
    private final double y;
    private final double z;
    private final int count;

    public SpawnExperienceOrbMessage(int entityId, double x, double y, double z, int count) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
    }

    public int getEntityId() {
        return entityId;
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

    public int getCount() {
        return count;
    }
}
