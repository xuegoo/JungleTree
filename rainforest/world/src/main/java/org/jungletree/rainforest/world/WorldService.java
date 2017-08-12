package org.jungletree.rainforest.world;

public interface WorldService {

    World getWorld(String name);

    Chunk getChunk(World world, int chunkX, int chunkZ);

    Block getBlock(World world, int blockX, int blockY, int blockZ);
}
