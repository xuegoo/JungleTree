package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class SpawnPlayerMessage implements Message {

    private final int entityId;
    private final String playerUuid;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    // TODO: Metadata
    // private final List<Entry> metadata;


    public SpawnPlayerMessage(int entityId, String playerUuid, double x, double y, double z, float pitch, float yaw) {
        this.entityId = entityId;
        this.playerUuid = playerUuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public int getEntityId() {
        return entityId;
    }

    public String getPlayerUuid() {
        return playerUuid;
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
}
