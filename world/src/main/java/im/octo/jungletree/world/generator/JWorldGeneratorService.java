package im.octo.jungletree.world.generator;

import com.google.inject.Singleton;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.exception.WorldGeneratorAlreadyRegisteredException;
import im.octo.jungletree.api.exception.WorldGeneratorNotFoundException;
import im.octo.jungletree.api.world.Dimension;
import im.octo.jungletree.api.world.World;
import im.octo.jungletree.api.world.generator.NormalWorldGenerator;
import im.octo.jungletree.api.world.generator.WorldGenerator;
import im.octo.jungletree.api.world.generator.WorldGeneratorService;
import im.octo.jungletree.world.JungleWorld;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class JWorldGeneratorService implements WorldGeneratorService {

    private static final int TEMP_MAX_WORLD_HEIGHT = 256;

    private final Map<String, WorldGenerator> customGenerators = new ConcurrentHashMap<>();

    public void registerGenerator(String name, WorldGenerator generator) {
        name = name.toLowerCase();
        if (customGenerators.containsKey(name)) {
            throw new WorldGeneratorAlreadyRegisteredException(name);
        }
        customGenerators.put(name, generator);
    }

    @Override
    public WorldGenerator getGenerator(String name) {
        name = name.toLowerCase();
        // TODO: Nether, The End
        if (name.equals(Dimension.OVERWORLD.name())) {
            return Rainforest.getServer().getGuice().getInstance(NormalWorldGenerator.class);
        }
        if (customGenerators.containsKey(name)) {
            return customGenerators.get(name);
        }
        throw new WorldGeneratorNotFoundException(name);
    }

    @Override
    public WorldGenerator getGenerator(Dimension dimension) {
        switch (dimension) {
            case OVERWORLD: return Rainforest.getServer().getGuice().getInstance(NormalWorldGenerator.class);
        }
        throw new WorldGeneratorNotFoundException(dimension.getName());
    }

    @Override
    public World createWorld(String name, Dimension dimension) {
        JungleWorld world = new JungleWorld();
        world.setName(name);
        world.setDimension(dimension);
        world.setMaxHeight(TEMP_MAX_WORLD_HEIGHT);
        return world;
    }
}
