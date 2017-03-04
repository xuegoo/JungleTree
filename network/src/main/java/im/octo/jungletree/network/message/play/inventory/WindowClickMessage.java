package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.inventory.ItemStack;
import im.octo.jungletree.network.message.play.player.ClickMode;

import java.util.Optional;

public class WindowClickMessage implements Message {

    private final int windowId;
    private final int slotId;
    private final int button;
    private final int actionId;
    private final int modeId;
    private final ItemStack clickedItem;

    public WindowClickMessage(int windowId, int slotId, int button, int actionId, int modeId, ItemStack clickedItem) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.button = button;
        this.actionId = actionId;
        this.modeId = modeId;
        this.clickedItem = clickedItem;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getButton() {
        return button;
    }

    public int getActionId() {
        return actionId;
    }

    public int getModeId() {
        return modeId;
    }

    public Optional<ClickMode> getMode() {
        return ClickMode.fromId(modeId);
    }

    public ItemStack getClickedItem() {
        return clickedItem;
    }
}
