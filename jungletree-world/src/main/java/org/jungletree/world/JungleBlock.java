package org.jungletree.world;

import org.jungletree.rainforest.world.Block;
import org.jungletree.rainforest.world.BlockType;

public class JungleBlock implements Block {

    private BlockType type;

    public JungleBlock(BlockType type) {
        this.type = type;
    }

    @Override
    public BlockType getType() {
        return type;
    }

    @Override
    public void setType(BlockType type) {
        this.type = type;
    }
}
