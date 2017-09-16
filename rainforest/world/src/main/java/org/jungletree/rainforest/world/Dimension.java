package org.jungletree.rainforest.world;

public enum Dimension {

    NETHER(-1),
    OVERWORLD(0),
    THE_END(1);

    private final int ordinal;

    Dimension(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public String getName() {
        return name();
    }
}
