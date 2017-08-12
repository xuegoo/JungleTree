package org.jungletree.rainforest.world;

public interface WorldLoader {

    World getWorld(String name);

    Chunk getChunk(World world, int chunkX, int chunkZ);
}
