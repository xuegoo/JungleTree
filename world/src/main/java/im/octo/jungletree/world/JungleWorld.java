package im.octo.jungletree.world;

import im.octo.jungletree.api.world.Dimension;
import im.octo.jungletree.api.world.World;

import java.util.UUID;

public class JungleWorld implements World {

    private UUID uuid;
    private String name;
    private long seed;
    private Dimension dimension;
    private int maxHeight;

    public UUID getUuid() {
        return uuid;
    }
    
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
