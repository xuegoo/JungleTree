package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

public class EntityTeleportMessage implements Message {

    private final int id;
    private final double x;
    private final double y;
    private final double z;
    private final int yaw;
    private final int pitch;
    private final boolean grounded;

    public EntityTeleportMessage(int id, double x, double y, double z, int yaw, int pitch, boolean grounded) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.grounded = grounded;
    }

    public int getId() {
        return id;
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

    public boolean isGrounded() {
        return grounded;
    }
}
