package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.inventory.ItemStack;

public class WindowItemMessage implements Message {

    private final int windowId;
    private final int count;
    private final ItemStack[] items;

    public WindowItemMessage(int windowId, int count, ItemStack[] items) {
        this.windowId = windowId;
        this.count = count;
        this.items = items;
    }

    public int getWindowId() {
        return windowId;
    }

    public int getCount() {
        return count;
    }

    public ItemStack[] getItems() {
        return items;
    }
}
