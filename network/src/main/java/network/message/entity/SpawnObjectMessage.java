package network.message.entity;

import com.flowpowered.network.Message;

import java.util.UUID;

public class SpawnObjectMessage implements Message {

    public static final int ITEM = 2;

    private final int id;
    private final UUID uuid;
    private final int type;
    private final double x;
    private final double y;
    private final double z;
    private final int pitch;
    private final int yaw;
    private final int data;
    private final int velocityX;
    private final int velocityY;
    private final int velocityZ;

    public SpawnObjectMessage(int id, UUID uuid, int type, double x, double y, double z, int pitch, int yaw, int data, int velocityX, int velocityY, int velocityZ) {
        this.id = id;
        this.uuid = uuid;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.data = data;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    public SpawnObjectMessage(int id, UUID uuid, int type, double x, double y, double z, int pitch, int yaw) {
        this(id, uuid, type, x, y, z, pitch, yaw, 0, 0, 0, 0);
    }

    public SpawnObjectMessage(int id, UUID uuid, int type, double x, double y, double z, int pitch, int yaw, int data) {
        this(id, uuid, type, x, y, z, pitch, yaw, data, 0, 0, 0);
    }

    public boolean hasFireball() {
        return data != 0;
    }
}
