package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.inventory.ItemStack;

public class CreativeInventoryActionMessage implements Message {

    private final int slot;
    private final ItemStack item;

    public CreativeInventoryActionMessage(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }
}
