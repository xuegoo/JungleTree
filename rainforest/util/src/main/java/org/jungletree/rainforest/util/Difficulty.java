package org.jungletree.rainforest.util;

public enum Difficulty {

    PEACEFUL(0),
    EASY(1),
    NORMAL(2),
    HARD(3);

    private final int ordinal;

    Difficulty(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public static Difficulty fromOrdinal(int ordinal) {
        for (Difficulty m : values()) {
            if (m.ordinal == ordinal) {
                return m;
            }
        }
        return PEACEFUL;
    }
}
