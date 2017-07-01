package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class WindowPropertyMessage implements Message {

    private final int windowId;
    private final int property;
    private final int value;

    public WindowPropertyMessage(int windowId, int property, int value) {
        this.windowId = windowId;
        this.property = property;
        this.value = value;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getProperty() {
        return property;
    }

    public int getValue() {
        return value;
    }
}
