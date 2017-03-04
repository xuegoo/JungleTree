package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class SpawnMobMessage implements Message {

    private final int entityId;
    private final String entityUuid;
    private final int type;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    private final float headPitch;
    private final int velocityX;
    private final int velocityY;
    private final int velocityZ;
    // TODO: Metadata
    // private List<Entry> metadata;


    public SpawnMobMessage(int entityId, String entityUuid, int type, double x, double y, double z, float pitch, float yaw, float headPitch, int velocityX, int velocityY, int velocityZ) {
        this.entityId = entityId;
        this.entityUuid = entityUuid;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.headPitch = headPitch;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    public int getEntityId() {
        return entityId;
    }

    public String getEntityUuid() {
        return entityUuid;
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

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getHeadPitch() {
        return headPitch;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public int getVelocityZ() {
        return velocityZ;
    }
}
