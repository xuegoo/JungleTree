package im.octo.jungletree.api.world;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.world.block.Block;

import java.util.Collection;
import java.util.UUID;

public interface World {

    UUID getUuid();

    String getName();

    long getSeed();

    void setSeed(long seed);

    Dimension getDimension();

    int getMaxHeight();

    void setMaxHeight(int maxHeight);

    int getSpawnChunksDiameter();

    Chunk getChunkAt(Location location);

    Chunk getChunk(int chunkX, int chunkZ);

    Block getBlockAt(Location location);

    Collection<Player> getPlayers();

    Location getSpawnLocation();
}
