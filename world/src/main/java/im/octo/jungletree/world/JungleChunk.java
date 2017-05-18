package im.octo.jungletree.world;

import im.octo.jungletree.api.world.Chunk;
import im.octo.jungletree.api.world.World;
import im.octo.jungletree.api.world.block.Block;

import java.util.UUID;

public class JungleChunk implements Chunk {

    private UUID uuid;
    private World world;
    private int x;
    private int z;

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public Block getBlock(int chunkX, int chunkY, int chunkZ) {
        return null;
    }
}
