package im.octo.jungletree.api.world.generator;

import im.octo.jungletree.api.world.Dimension;

public interface WorldGeneratorService {

    WorldGenerator getGenerator(String name);

    WorldGenerator getGenerator(Dimension dimension);
}
