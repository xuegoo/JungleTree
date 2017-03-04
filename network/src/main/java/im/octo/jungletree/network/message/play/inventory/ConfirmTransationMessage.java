package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class ConfirmTransationMessage implements Message {

    private final int windowId;
    private final int actionId;
    private final boolean accepted;

    public ConfirmTransationMessage(int windowId, int actionId, boolean accepted) {
        this.windowId = windowId;
        this.actionId = actionId;
        this.accepted = accepted;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getActionId() {
        return actionId;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
