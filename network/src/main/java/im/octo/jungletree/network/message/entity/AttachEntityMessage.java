package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

public class AttachEntityMessage implements Message {

    private final int attached;
    private final int holding;

    public AttachEntityMessage(int attached, int holding) {
        this.attached = attached;
        this.holding = holding;
    }

    public int getAttached() {
        return attached;
    }

    public int getHolding() {
        return holding;
    }
}
