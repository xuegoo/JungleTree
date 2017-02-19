package network.message.entity;

import com.flowpowered.network.Message;

public class VehicleMoveMessage implements Message {

    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;

    public VehicleMoveMessage(double x, double y, double z, float pitch, float yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
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
