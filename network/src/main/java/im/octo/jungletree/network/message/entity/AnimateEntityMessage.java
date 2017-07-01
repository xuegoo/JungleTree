package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

import java.util.Optional;

public class AnimateEntityMessage implements Message {

    private final int id;
    private final int animation;

    public AnimateEntityMessage(int id, int animation) {
        this.id = id;
        this.animation = animation;
    }

    public int getId() {
        return id;
    }

    public AnimateType getAnimateType() {
        return AnimateType.fromId(id).orElseThrow(() -> new InvalidAnimateTypeException(id));
    }

    public int getAnimation() {
        return animation;
    }

    public enum AnimateType {
        SWING_MAIN_HAND(0),
        TAKE_DAMAGE(1),
        LEAVE_BED(2),
        SWING_OFF_HAND(3),
        CRIT(4),
        MAGIC_CRIT(5);

        private final int id;

        AnimateType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Optional<AnimateType> fromId(int id) {
            for (AnimateType value : values()) {
                if (value.id == id) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }

    class InvalidAnimateTypeException extends RuntimeException {
        InvalidAnimateTypeException(int id) {
            super(String.format("Animate type with id of %d is not known", id));
        }
    }
}
