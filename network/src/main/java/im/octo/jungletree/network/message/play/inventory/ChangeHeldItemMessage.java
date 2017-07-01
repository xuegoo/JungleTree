package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class ChangeHeldItemMessage implements Message {

    private final int slot;

    public ChangeHeldItemMessage(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
