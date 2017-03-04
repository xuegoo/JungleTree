package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class EnchantItemMessage implements Message {

    private final int window;
    private final int enchantment;

    public EnchantItemMessage(int window, int enchantment) {
        this.window = window;
        this.enchantment = enchantment;
    }

    public int getWindow() {
        return window;
    }

    public int getEnchantment() {
        return enchantment;
    }
}
