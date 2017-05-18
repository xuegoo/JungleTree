package im.octo.jungletree.api.world.block;

import im.octo.jungletree.api.world.Chunk;
import im.octo.jungletree.api.world.Location;
import im.octo.jungletree.api.world.World;

import java.util.UUID;

public interface Block {

    UUID getUuid();

    void setUuid(UUID uuid);

    Material getMaterial();

    void setMaterial(Material material);

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getZ();

    void setZ(int z);

    Chunk getChunk();

    void setChunk(Chunk chunk);

    default Location getLocation() {
        return new Location(getChunk().getWorld(), getX(), getY(), getZ());
    }
}
