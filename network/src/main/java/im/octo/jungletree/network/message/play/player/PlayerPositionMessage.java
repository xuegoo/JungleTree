package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class PlayerPositionMessage implements Message {

    private final double playerX;
    private final double playerFeetY;
    private final double playerZ;
    private final boolean grounded;

    public PlayerPositionMessage(double playerX, double playerFeetY, double playerZ, boolean grounded) {
        this.playerX = playerX;
        this.playerFeetY = playerFeetY;
        this.playerZ = playerZ;
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

    public boolean isGrounded() {
        return grounded;
    }
}
