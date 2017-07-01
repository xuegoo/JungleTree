package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

import java.util.UUID;

public class SpawnPlayerMessage implements Message {

    private final int id;
    private final UUID uuid;
    private final double x;
    private final double y;
    private final double z;
    private final int yaw;
    private final int pitch;
    // TODO: Update when metadata is ready
    // private final List<Entry> metadata;


    public SpawnPlayerMessage(int id, UUID uuid, double x, double y, double z, int yaw, int pitch) {
        this.id = id;
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
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
}
