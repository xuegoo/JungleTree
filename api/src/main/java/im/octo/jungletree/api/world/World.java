package im.octo.jungletree.api.world;

import im.octo.jungletree.api.entity.Player;

import java.util.Collection;
import java.util.UUID;

public interface World {

    UUID getUuid();

    String getName();

    Dimension getDimension();

    Chunk getChunkAt(Location location);

    Block getBlockAt(Location location);

    Collection<Player> getPlayers();

    Location getSpawnLocation();
}
