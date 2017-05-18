package im.octo.jungletree.world;

import im.octo.jungletree.api.world.Chunk;
import im.octo.jungletree.api.world.block.Block;
import im.octo.jungletree.api.world.block.Material;

import java.util.UUID;

public class JungleBlock implements Block {

    static final String HASH_CODE_PREFIX = "JTB|";
    static final String HASH_CODE_SEPARATOR = ",";

    private UUID uuid;
    private int x;
    private int y;
    private int z;
    private Material material;
    private Chunk chunk;

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public Chunk getChunk() {
        return chunk;
    }

    @Override
    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }
}
