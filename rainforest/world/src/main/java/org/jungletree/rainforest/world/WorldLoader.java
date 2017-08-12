package org.jungletree.rainforest.world;

import java.util.concurrent.CompletableFuture;

public interface WorldLoader {

    World getWorld(String name);

    void loadChunk(World world, int chunkX, int chunkZ);

    CompletableFuture<Chunk> getChunk(World world, int chunkX, int chunkZ);
}
