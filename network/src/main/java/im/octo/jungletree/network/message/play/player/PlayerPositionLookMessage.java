package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.world.Location;

public class PlayerPositionLookMessage implements Message {

    private final double playerX;
    private final double playerFeetY;
    private final double playerZ;
    private final float pitch;
    private final float yaw;
    private final int flags;
    private final int teleportId;

    public PlayerPositionLookMessage(double playerX, double playerFeetY, double playerZ, float yaw, float pitch, int flags, int teleportId) {
        this.playerX = playerX;
        this.playerFeetY = playerFeetY;
        this.playerZ = playerZ;
        this.yaw = (yaw % 360 + 360) % 360;
        this.pitch = pitch;
        this.flags = flags;
        this.teleportId = teleportId;
    }

    public PlayerPositionLookMessage(double playerX, double playerFeetY, double playerZ, float pitch, float yaw) {
        this(playerX, playerFeetY, playerZ, pitch, yaw, 0, 0);
    }

    public PlayerPositionLookMessage(Location location) {
        this(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
    }

    public double getPlayerX() {
        return playerX;
    }

    public double getPlayerFeetY() {
        return playerFeetY;
    }

    public double getPlayerZ() {
        return playerZ;
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
