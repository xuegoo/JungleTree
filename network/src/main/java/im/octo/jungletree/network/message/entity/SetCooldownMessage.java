package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

public class SetCooldownMessage implements Message {

    private final int itemId;
    private final int cooldownTicks;

    public SetCooldownMessage(int itemId, int cooldownTicks) {
        this.itemId = itemId;
        this.cooldownTicks = cooldownTicks;
    }

    public int getItemId() {
        return itemId;
    }

    public int getCooldownTicks() {
        return cooldownTicks;
    }
}
