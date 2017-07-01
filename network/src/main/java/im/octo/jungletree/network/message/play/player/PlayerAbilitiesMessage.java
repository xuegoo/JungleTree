package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class PlayerAbilitiesMessage implements Message {

    private final int flags;
    private final float flyingSpeed;
    private final float walkingSpeed;

    public PlayerAbilitiesMessage(int flags, float flyingSpeed, float walkingSpeed) {
        this.flags = flags;
        this.flyingSpeed = flyingSpeed;
        this.walkingSpeed = walkingSpeed;
    }

    public int getFlags() {
        return flags;
    }

    public float getFlyingSpeed() {
        return flyingSpeed;
    }

    public float getWalkingSpeed() {
        return walkingSpeed;
    }
}
