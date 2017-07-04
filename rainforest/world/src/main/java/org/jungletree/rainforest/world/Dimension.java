package org.jungletree.rainforest.world;

public enum Dimension {

    NETHER(-1),
    OVERWORLD(0),
    THE_END(1);

    private final int level;

    Dimension(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name();
    }
}
