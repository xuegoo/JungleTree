package im.octo.jungletree.world.generator;

import com.google.inject.Singleton;
import im.octo.jungletree.api.world.Chunk;
import im.octo.jungletree.api.world.World;
import im.octo.jungletree.api.world.generator.NormalWorldGenerator;

@Singleton
public class JNormalWorldGenerator implements NormalWorldGenerator {

    @Override
    public void generate(World world) {
    }

    @Override
    public Chunk generateChunk(World world, int x, int z) {
        return null;
    }
}
