package im.octo.jungletree.api.world;

import im.octo.jungletree.api.entity.Player;

import java.util.Collection;

public interface World {

    String getName();

    Chunk getChunkAt(Location location);

    Block getBlockAt(Location location);

    Collection<Player> getPlayers();
}
