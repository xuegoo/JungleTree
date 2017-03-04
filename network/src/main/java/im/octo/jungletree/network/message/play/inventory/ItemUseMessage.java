package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class ItemUseMessage implements Message {

    private final int hand;

    public ItemUseMessage(int hand) {
        this.hand = hand;
    }

    public int getHand() {
        return hand;
    }
}
