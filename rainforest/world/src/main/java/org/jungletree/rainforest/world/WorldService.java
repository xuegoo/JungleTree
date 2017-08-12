package org.jungletree.rainforest.world;

import java.util.concurrent.CompletableFuture;

public interface WorldService {

    World getWorld(String name);

    CompletableFuture<Chunk> getChunk(World world, int chunkX, int chunkZ);

    CompletableFuture<Block> getBlock(World world, int blockX, int blockY, int blockZ);
}
