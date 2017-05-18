package im.octo.jungletree.api.world;

import im.octo.jungletree.api.world.block.Block;

import java.util.UUID;

public interface Chunk {

    int DIAMETER = 16;

    UUID getUuid();

    World getWorld();

    int getX();

    int getZ();

Block getBlock(int chunkX, int chunkY, int chunkZ);
}
