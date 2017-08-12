package org.jungletree.world;

import org.jungletree.rainforest.storage.StorageService;
import org.jungletree.rainforest.world.Chunk;
import org.jungletree.rainforest.world.World;
import org.jungletree.rainforest.world.WorldLoader;
import org.jungletree.world.exception.WorldNotPresentException;
import org.redisson.api.RBucket;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.stream.IntStream;

@Singleton
public class JungleWorldLoader implements WorldLoader {

    private StorageService storage;

    @Inject
    public JungleWorldLoader(StorageService storage) {
        this.storage = storage;
    }

    @Override
    public World getWorld(String name) {
        RBucket<World> bucket = storage.getStorage().getBucket("world-" + name);
        if (!bucket.isExists()) {
            throw new WorldNotPresentException(name);
        }
        return bucket.get();
    }

    @Override
    public Chunk getChunk(World world, int chunkX, int chunkZ) {
        Map<ChunkReference, JungleChunk> chunks = storage.getStorage().getMap("chunks-" + world.getName());
        ChunkReference reference = new ChunkReference(chunkX, chunkZ);
        if (chunks.containsKey(reference)) {
            JungleChunk chunk = chunks.get(reference);
            initBlocksForChunk(world, chunk);
            return chunk;
        }
        // TODO: Don't return null
        return null;
    }

    private JungleBlock[][][] initBlocksForChunk(World world, Chunk chunk) {
        int maxWorldHeight = world.getMaxHeight();
        JungleBlock[][][] blocks = new JungleBlock[maxWorldHeight][16][16];
        Map<BlockReference, JungleBlock> blockReferences = storage.getStorage().getMap(String.format("chunk-%s-%s", chunk.getX(), chunk.getZ()));
        initBlocksCoordY(blocks, blockReferences);
        return blocks;
    }

    private void initBlocksCoordY(JungleBlock[][][] blocks, Map<BlockReference, JungleBlock> blockReferences) {
        IntStream.range(0, blocks.length).parallel().forEach(y -> {
            blocks[y] = new JungleBlock[16][16];

            initBlocksCoordX(blocks, blockReferences, y);
        });
    }

    private void initBlocksCoordX(JungleBlock[][][] blocks, Map<BlockReference, JungleBlock> blockReferences, int y) {
        IntStream.range(0, blocks.length).parallel().forEach(x -> {
            blocks[y][x] = new JungleBlock[16];
            initBlocksCoordZ(blocks, blockReferences, y, x);
        });
    }

    private void initBlocksCoordZ(JungleBlock[][][] blocks, Map<BlockReference, JungleBlock> blockReferences, int y, int x) {
        IntStream.range(0, blocks.length).parallel().forEach(z -> blocks[y][x][z] = blockReferences.getOrDefault(new BlockReference(x, y, z), new JungleBlock()));
    }
}
