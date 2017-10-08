package org.jungletree.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jungletree.rainforest.scheduler.SchedulerService;
import org.jungletree.rainforest.storage.StorageService;
import org.jungletree.rainforest.util.Difficulty;
import org.jungletree.rainforest.util.GameMode;
import org.jungletree.rainforest.world.*;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class JungleWorldLoader implements WorldLoader {

    private static final Logger log = LoggerFactory.getLogger(JungleWorldLoader.class);
    private static final Gson GSON = new GsonBuilder().create();

    private final SchedulerService scheduler;
    private final StorageService storage;

    public JungleWorldLoader() {
        this.scheduler = ServiceLoader.load(SchedulerService.class).findFirst().orElseThrow(NoSuchElementException::new);
        this.storage = ServiceLoader.load(StorageService.class).findFirst().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public World getWorld(String name) {
        Map<String, World> worlds = storage.getStorage().getMap("worlds");
        return worlds.getOrDefault(name, temporaryCreateWorld());
    }

    @Override
    public void loadChunk(World world, int chunkX, int chunkZ) {
        RMap<Integer, JungleChunk> chunks = storage.getStorage().getMap(String.format("world-%s_chunks", world.getName()));

        scheduler.execute(() -> {
            int code = Objects.hash(chunkX, chunkZ);
            if (chunks.containsKey(code)) {
                log.info("Loading chunk: x={}, z={}", chunkX, chunkZ);
                JungleChunk chunk = chunks.get(code);
                chunk.setBlocks(initBlocksForChunk(world, chunk));
            } else {
                log.info("Generating chunk: x={}, z={}", chunkX, chunkZ);
                RLock lock = chunks.getLock(code);
                JungleChunk chunk = temporaryCreateEmptyChunk(new ChunkReference(chunkX, chunkZ));
                chunk.setBlocks(initBlocksForChunk(world, chunk));
                lock.unlock();
                chunks.fastPut(code, chunk);
            }

        });
    }

    private World temporaryCreateWorld() {
        World world = new World();
        world.setUniqueId(UUID.randomUUID());
        world.setName("test");
        world.setGameMode(GameMode.SURVIVAL);
        world.setDimension(Dimension.OVERWORLD);
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setSeed(ThreadLocalRandom.current().nextLong());
        world.setMaxHeight(256);
        world.setSpawnRadius(16);
        return world;
    }

    @Override
    public CompletableFuture<Chunk> getChunk(World world, int chunkX, int chunkZ) {
        return null;
    }

    private JungleChunk temporaryCreateEmptyChunk(ChunkReference chunkReference) {
        JungleChunk chunk = new JungleChunk();
        chunk.setX(chunkReference.getChunkX());
        chunk.setZ(chunkReference.getChunkZ());
        return chunk;
    }

    private JungleBlock[][][] initBlocksForChunk(World world, Chunk chunk) {
        RBucket<String> bucket = storage.getStorage().getBucket(String.format("world-%s_chunk-%d-%d_blocks", world.getName(), chunk.getX(), chunk.getZ()));

        if (bucket.isExists()) {
            return GSON.fromJson(bucket.get(), JungleBlock[][][].class);
        }

        int maxWorldHeight = world.getMaxHeight();
        JungleBlock[][][] blocks = new JungleBlock[maxWorldHeight][16][16];

        IntStream.range(0, 16).parallel().forEach(y -> {
            blocks[y] = new JungleBlock[16][16];
            IntStream.range(0, 16).forEach(x -> {
                blocks[y][x] = new JungleBlock[16];
                IntStream.range(0, 16).forEach(z -> {
                    blocks[y][x][z] = new JungleBlock();
                    blocks[y][x][z].setType(y < 32 ? BlockType.STONE : BlockType.AIR);
                });
            });
        });

        log.info("toJson");
        scheduler.execute(() -> bucket.set(GSON.toJson(blocks)));
        return blocks;
    }
}
