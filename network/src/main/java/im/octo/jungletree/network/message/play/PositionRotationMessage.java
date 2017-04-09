package im.octo.jungletree.network.message.play;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.world.Location;

public class PositionRotationMessage implements Message {

    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    private final int flags;
    private final int teleportId;

    public PositionRotationMessage(double x, double y, double z, float pitch, float yaw, int flags, int teleportId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.flags = flags;
        this.teleportId = teleportId;
    }

    public PositionRotationMessage(double x, double y, double z, float pitch, float yaw) {
        this(x, y, z, pitch, yaw, 0, 0);
    }

    public PositionRotationMessage(Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
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

    public int getFlags() {
        return flags;
    }

    public int getTeleportId() {
        return teleportId;
    }
}
