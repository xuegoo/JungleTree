package world;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.world.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JungleWorld implements World {

    private final Set<Player> players = Collections.synchronizedSet(new HashSet<>());

    @Override
    public String getName() {
        return "Dummy";
    }

    @Override
    public Dimension getDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public Chunk getChunkAt(Location location) {
        return null;
    }

    @Override
    public Block getBlockAt(Location location) {
        return null;
    }

    @Override
    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(this, 0, 0, 0);
    }
}
