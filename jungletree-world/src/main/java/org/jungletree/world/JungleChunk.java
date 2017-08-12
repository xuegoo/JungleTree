package org.jungletree.world;

import org.jungletree.rainforest.world.Block;
import org.jungletree.rainforest.world.Chunk;

public class JungleChunk implements Chunk {

    private int x;
    private int z;

    private JungleBlock[][][] blocks;

    @Override
    public int getX() {
        return 0;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getZ() {
        return 0;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public Block getBlock(int blockX, int blockY, int blockZ) {
        return blocks[blockY][blockX][blockZ];
    }
}
