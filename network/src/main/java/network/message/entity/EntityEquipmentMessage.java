package network.message.entity;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.inventory.ItemStack;

import java.util.Optional;

public class EntityEquipmentMessage implements Message {

    private final int id;
    private final int slot;
    private final ItemStack stack;

    public EntityEquipmentMessage(int id, int slot, ItemStack stack) {
        this.id = id;
        this.slot = slot;
        this.stack = stack;
    }

    public int getId() {
        return id;
    }

    public EquipmentType getEquipmentType() {
        return EquipmentType.fromId(id).orElseThrow(() -> new InvalidEquipmentTypeException(id));
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getStack() {
        return stack;
    }

    public enum EquipmentType {
        HELD_ITEM(0),
        OFF_HAND(1),
        BOOTS_SLOT(2),
        LEGGINS_SLOT(3),
        CHESTPLAT_SLOT(4),
        HELMET_SLOT(5);

        private final int id;

        EquipmentType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Optional<EquipmentType> fromId(int id) {
            for (EquipmentType value : values()) {
                if (value.id == id) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }

    class InvalidEquipmentTypeException extends RuntimeException {
        InvalidEquipmentTypeException(int id) {
            super(String.format("Equipment type with id of %d is not known", id));
        }
    }
}
