package org.jungletree.world;

import org.jungletree.rainforest.world.Dimension;
import org.jungletree.rainforest.world.World;

import java.util.Set;
import java.util.UUID;

public class JungleWorld implements World {

    private UUID uniqueId;
    private String name;
    private long seed;
    private Dimension dimension;
    private int maxHeight;
    private int spawnRadius;

    private Set<ChunkReference> chunkReferences;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public int getSpawnRadius() {
        return spawnRadius;
    }

    @Override
    public void setSpawnRadius(int spawnRadius) {
        this.spawnRadius = spawnRadius;
    }

    public Set<ChunkReference> getChunkReferences() {
        return chunkReferences;
    }

    public void setChunkReferences(Set<ChunkReference> chunkReferences) {
        this.chunkReferences = chunkReferences;
    }
}
