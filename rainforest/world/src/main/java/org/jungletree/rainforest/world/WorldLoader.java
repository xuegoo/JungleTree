package org.jungletree.rainforest.world;

public interface WorldLoader {

    World getWorld(String name);

    Chunk getChunk(String worldName, int chunkX, int chunkZ);
}
