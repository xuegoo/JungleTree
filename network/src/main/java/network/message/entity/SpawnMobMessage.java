package network.message.entity;

import com.flowpowered.network.Message;

import java.util.UUID;

public class SpawnMobMessage implements Message {

    private final int id;
    private final UUID uuid;
    private final int type;
    private final double x;
    private final double y;
    private final double z;
    private final int yaw;
    private final int pitch;
    private final int headPitch;
    private final int velocityX;
    private final int velocityY;
    private final int velocityZ;
    // TODO: Metadata
    // private final List<Entry> metadata;

    // TODO: Don't forget to update constructor with metadata arrival
    public SpawnMobMessage(int id, UUID uuid, int type, double x, double y, double z, int yaw, int pitch, int headPitch, int velocityX, int velocityY, int velocityZ) {
        this.id = id;
        this.uuid = uuid;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.headPitch = headPitch;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
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

    public int getYaw() {
        return yaw;
    }

    public int getPitch() {
        return pitch;
    }

    public int getHeadPitch() {
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
