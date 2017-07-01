package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class PlayerLookMessage implements Message {

    private final float yaw;
    private final float pitch;

    public PlayerLookMessage(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
