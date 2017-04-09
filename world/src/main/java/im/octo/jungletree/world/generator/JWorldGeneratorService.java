package im.octo.jungletree.world.generator;

import com.google.inject.Singleton;
import im.octo.jungletree.api.world.Dimension;
import im.octo.jungletree.api.world.generator.WorldGenerator;
import im.octo.jungletree.api.world.generator.WorldGeneratorService;

@Singleton
public class JWorldGeneratorService implements WorldGeneratorService {

    @Override
    public WorldGenerator getGenerator(String name) {
        return null;
    }

    @Override
    public WorldGenerator getGenerator(Dimension dimension) {
        return null;
    }
}
