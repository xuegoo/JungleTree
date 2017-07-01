package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class PlayerSteerBoatMessage implements Message {

    private final boolean leftPaddle;
    private final boolean rightPaddle;

    public PlayerSteerBoatMessage(boolean leftPaddle, boolean rightPaddle) {
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
    }

    public boolean isLeftPaddle() {
        return leftPaddle;
    }

    public boolean isRightPaddle() {
        return rightPaddle;
    }
}
