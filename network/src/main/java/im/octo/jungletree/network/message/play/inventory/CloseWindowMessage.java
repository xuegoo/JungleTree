package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class CloseWindowMessage implements Message {

    private final int windowId;

    public CloseWindowMessage(int windowId) {
        this.windowId = windowId;
    }

    public int getWindowId() {
        return windowId;
    }
}
