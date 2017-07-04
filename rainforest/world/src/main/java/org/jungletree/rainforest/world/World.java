package org.jungletree.rainforest.world;

import java.util.UUID;

public interface World {

    UUID getUuid();

    String getName();

    void setName(String name);

    long getSeed();

    void setSeed(long seed);

    Dimension getDimension();

    void setDimension(Dimension dimension);

    int getMaxHeight();

    void setMaxHeight(int maxHeight);
}
