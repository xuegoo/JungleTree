package org.jungletree.world;

import java.util.Objects;

public class ChunkReference {

    private final int chunkX;
    private final int chunkZ;

    public ChunkReference(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkReference that = (ChunkReference) o;
        return chunkX == that.chunkX &&
                chunkZ == that.chunkZ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chunkX, chunkZ);
    }
}
