package org.jungletree.rainforest.world;

public interface Chunk {

    int getX();

    int getZ();

    Block getBlock(int blockX, int blockY, int blockZ);
}
