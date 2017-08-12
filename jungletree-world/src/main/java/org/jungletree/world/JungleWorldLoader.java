package org.jungletree.world;

import org.jungletree.rainforest.storage.StorageService;
import org.jungletree.rainforest.world.Chunk;
import org.jungletree.rainforest.world.World;
import org.jungletree.rainforest.world.WorldLoader;
import org.jungletree.world.exception.WorldNotPresentException;
import org.redisson.api.RBucket;

import javax.inject.Inject;
import java.util.Map;

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
    public Chunk getChunk(String worldName, int chunkX, int chunkZ) {
        Map<ChunkReference, JungleChunk> chunks = storage.getStorage().getMap("chunks-" + worldName);
        ChunkReference reference = new ChunkReference(chunkX, chunkZ);
        if (chunks.containsKey(reference)) {
            return chunks.get(reference);
        }
        return null;
    }
}
