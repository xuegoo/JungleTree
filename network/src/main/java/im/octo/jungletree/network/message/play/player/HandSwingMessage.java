package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class HandSwingMessage implements Message {

    private final int hand;

    public HandSwingMessage(int hand) {
        this.hand = hand;
    }

    public int getHand() {
        return hand;
    }
}
