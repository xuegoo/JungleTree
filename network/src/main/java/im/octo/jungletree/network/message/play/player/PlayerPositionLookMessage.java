package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class PlayerPositionLookMessage implements Message {

    private final double playerX;
    private final double playerFeetY;
    private final double playerZ;
    private final double pitch;
    private final double yaw;
    private final boolean grounded;

    public PlayerPositionLookMessage(double playerX, double playerFeetY, double playerZ, double pitch, double yaw, boolean grounded) {
        this.playerX = playerX;
        this.playerFeetY = playerFeetY;
        this.playerZ = playerZ;
        this.pitch = pitch;
        this.yaw = yaw;
        this.grounded = grounded;
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

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }

    public boolean isGrounded() {
        return grounded;
    }
}
