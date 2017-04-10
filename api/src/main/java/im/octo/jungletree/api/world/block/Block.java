package im.octo.jungletree.api.world.block;

import im.octo.jungletree.api.world.Chunk;
import im.octo.jungletree.api.world.Location;
import im.octo.jungletree.api.world.World;

import java.util.UUID;

public interface Block {

    UUID getUuid();

    Material getMaterial();

    void setMaterial(Material material);

    int getX();

    int getY();

    int getZ();

    Chunk getChunk();

    World getWorld();

    default Location getLocation() {
        return new Location(getWorld(), getX(), getY(), getZ());
    }
}
