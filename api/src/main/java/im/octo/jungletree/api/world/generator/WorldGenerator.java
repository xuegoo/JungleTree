package im.octo.jungletree.api.world.generator;

import im.octo.jungletree.api.world.Chunk;
import im.octo.jungletree.api.world.World;

public interface WorldGenerator {

    void generate(World world);

    Chunk generateChunk(World world, int x, int z);
}
