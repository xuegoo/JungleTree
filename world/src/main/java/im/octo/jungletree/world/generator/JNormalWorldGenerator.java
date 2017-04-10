package im.octo.jungletree.world.generator;

import com.google.inject.Singleton;
import im.octo.jungletree.api.HibernateService;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.exception.IllegalOperationException;
import im.octo.jungletree.api.world.Chunk;
import im.octo.jungletree.api.world.World;
import im.octo.jungletree.api.world.block.Material;
import im.octo.jungletree.api.world.generator.NormalWorldGenerator;
import im.octo.jungletree.world.JungleBlock;
import im.octo.jungletree.world.JungleChunk;
import im.octo.jungletree.world.JungleWorld;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Singleton
public class JNormalWorldGenerator implements NormalWorldGenerator {

    @Override
    public void generate(World world) {
        int diameter = world.getSpawnChunksDiameter();

        for (int x=0; x<diameter; x++) {
            int finalX = x;
            IntStream.range(0, diameter).forEach(z -> ((JungleWorld)world).setChunk(generateChunk(world, finalX, z)));
        }
    }

    @Override
    public Chunk generateChunk(World world, int chunkPosX, int chunkPosZ) {
        if (!(world instanceof JungleWorld)) {
            throw new IllegalOperationException("World is not of type " + JungleWorld.class.getSimpleName() + ". What on Earth are you doing?");
        }
        JungleChunk chunk = new JungleChunk();
        chunk.setWorld((JungleWorld) world);
        chunk.setX(chunkPosX);
        chunk.setZ(chunkPosZ);
        chunk.updateMapKey();

        for (int x=0; x<Chunk.DIAMETER; x++) {
            for (int z=0; z<Chunk.DIAMETER; z++) {
                int finalX = x;
                int finalZ = z;
                IntStream.range(0, world.getMaxHeight()).forEach(y -> {
                    JungleBlock block = new JungleBlock();
                    block.setChunk(chunk);
                    block.setX(finalX);
                    block.setY(y);
                    block.setZ(finalZ);
                    block.updateMapKey();

                    if (y > 32) {
                        block.setMaterial(Material.AIR);
                    } else {
                        block.setMaterial(Material.STONE);
                    }
                    chunk.setBlock(block);
                });
            }
        }

        return chunk;
    }
}
