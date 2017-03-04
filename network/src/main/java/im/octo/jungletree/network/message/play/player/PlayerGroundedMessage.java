package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class PlayerGroundedMessage implements Message {

    private final boolean grounded;

    public PlayerGroundedMessage(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isGrounded() {
        return grounded;
    }
}
