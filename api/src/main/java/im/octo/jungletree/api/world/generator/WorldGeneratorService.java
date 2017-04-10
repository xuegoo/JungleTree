package im.octo.jungletree.api.world.generator;

import im.octo.jungletree.api.world.Dimension;
import im.octo.jungletree.api.world.World;

public interface WorldGeneratorService {

    WorldGenerator getGenerator(String name);

    WorldGenerator getGenerator(Dimension dimension);

    World createWorld(String name, Dimension dimension);
}
