package im.octo.jungletree.network.message.play.player;

import java.util.Optional;

public enum ClickMode {

    NORMAL(0),
    SHIFT_CLICK(1),
    NUMBER_SELECT(2),
    MIDDLE_CLICK_CREATIVE(3),
    DROP_ITEM(4),
    DRAG_CLICK(5),
    DOUBLE_CLICK(6);

    private final int mode;

    ClickMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public static Optional<ClickMode> fromId(int id) {
        for (ClickMode m : values()) {
            if (m.mode == id) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }
}
