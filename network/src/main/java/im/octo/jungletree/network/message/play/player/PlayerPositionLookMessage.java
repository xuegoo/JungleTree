package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.world.Location;

public class PlayerPositionLookMessage implements Message {

    private final boolean grounded;
    private final double playerX;
    private final double playerFeetY;
    private final double playerZ;
    private final float pitch;
    private final float yaw;
    private final int flags;
    private final int teleportId;

    public PlayerPositionLookMessage(boolean grounded, double playerX, double playerFeetY, double playerZ, float yaw, float pitch, int flags, int teleportId) {
        this.grounded = grounded;
        this.playerX = playerX;
        this.playerFeetY = playerFeetY;
        this.playerZ = playerZ;
        this.yaw = (yaw % 360 + 360) % 360;
        this.pitch = pitch;
        this.flags = flags;
        this.teleportId = teleportId;
    }

    public PlayerPositionLookMessage(boolean grounded, double playerX, double playerFeetY, double playerZ, float pitch, float yaw) {
        this(grounded, playerX, playerFeetY, playerZ, pitch, yaw, 0, 0);
    }

    public PlayerPositionLookMessage(boolean grounded, Location location) {
        this(grounded, location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
    }

    public boolean isGrounded() {
        return grounded;
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

    public void update(Location location) {
        location.setX(playerX);
        location.setY(playerFeetY);
        location.setZ(playerZ);
        location.setYaw(yaw);
        location.setPitch(pitch);
    }
}
