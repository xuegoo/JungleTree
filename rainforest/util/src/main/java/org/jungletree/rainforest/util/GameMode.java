package org.jungletree.rainforest.util;

public enum GameMode {

    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private final int ordinal;

    GameMode(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public static GameMode fromOrdinal(int ordinal) {
        for (GameMode m : values()) {
            if (m.ordinal == ordinal) {
                return m;
            }
        }
        return SURVIVAL;
    }
}
