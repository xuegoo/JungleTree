package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class OpenWindowMessage implements Message {

    private final int windowId;
    private final int windowType;
    private final String title;
    private final int slotCount;
    private final int entityId;

    public OpenWindowMessage(int windowId, int windowType, String title, int slotCount, int entityId) {
        this.windowId = windowId;
        this.windowType = windowType;
        this.title = title;
        this.slotCount = slotCount;
        this.entityId = entityId;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getWindowType() {
        return windowType;
    }

    public String getTitle() {
        return title;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public int getEntityId() {
        return entityId;
    }
}
