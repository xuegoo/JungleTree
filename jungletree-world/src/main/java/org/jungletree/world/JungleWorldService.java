package org.jungletree.world;

import org.jungletree.rainforest.world.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JungleWorldService implements WorldService {

    private final WorldLoader worldLoader;

    @Inject
    public JungleWorldService(WorldLoader worldLoader) {
        this.worldLoader = worldLoader;
    }

    @Override
    public World getWorld(String name) {
        return worldLoader.getWorld(name);
    }

    @Override
    public Chunk getChunk(World world, int chunkX, int chunkZ) {
        return worldLoader.getChunk(world, chunkX, chunkZ);
    }

    @Override
    public Block getBlock(World world, int blockX, int blockY, int blockZ) {
        Chunk chunk = getChunk(
                world,
                (int) Math.floor(blockX / 16),
                (int) Math.floor(blockZ / 16)
        );

        return chunk.getBlock(
                blockX % 16,
                blockY,
                blockZ % 16
        );
    }
}
