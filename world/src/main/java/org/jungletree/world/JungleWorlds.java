package org.jungletree.world;

import org.jungletree.rainforest.world.*;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;

public class JungleWorlds implements Worlds {

    private final WorldLoader worldLoader;

    public JungleWorlds() {
        this.worldLoader = ServiceLoader.load(WorldLoader.class).findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public World getWorld(String name) {
        return worldLoader.getWorld(name);
    }

    @Override
    public CompletableFuture<Chunk> getChunk(World world, int chunkX, int chunkZ) {
        return worldLoader.getChunk(world, chunkX, chunkZ);
    }

    @Override
    public CompletableFuture<Block> getBlock(World world, int blockX, int blockY, int blockZ) {
        return getChunk(
                world,
                (int) Math.floor(blockX / 16),
                (int) Math.floor(blockZ / 16)
        ).thenApply(chunk -> chunk.getBlock(blockX % 16, blockY, blockZ % 16));
    }
}
