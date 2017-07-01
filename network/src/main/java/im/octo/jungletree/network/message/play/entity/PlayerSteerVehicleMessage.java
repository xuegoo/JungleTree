package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class PlayerSteerVehicleMessage implements Message {

    private final float strafe;
    private final float forwards;
    private final int flags;

    public PlayerSteerVehicleMessage(float strafe, float forwards, int flags) {
        this.strafe = strafe;
        this.forwards = forwards;
        this.flags = flags;
    }

    public float getStrafe() {
        return strafe;
    }

    public float getForwards() {
        return forwards;
    }

    public int getFlags() {
        return flags;
    }
}
