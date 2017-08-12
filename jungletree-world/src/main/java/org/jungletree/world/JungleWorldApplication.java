package org.jungletree.world;

import com.google.inject.Guice;
import com.google.inject.Inject;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.world.World;
import org.jungletree.rainforest.world.WorldLoader;
import org.jungletree.world.messaging.WorldRequestMessageHandler;
import org.jungletree.world.messaging.WorldRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class JungleWorldApplication {

    public static final String MESSENGER_NAME = "WORLD_STORAGE";

    private static final Logger log = LoggerFactory.getLogger(JungleWorldApplication.class);

    @Inject
    private WorldLoader worldLoader;

    @Inject
    private MessagingService messaging;

    @Inject
    private WorldRequestMessageHandler worldRequestHandler;

    private JungleWorldApplication() {
        Guice.createInjector(new JungleWorldGuiceModule()).injectMembers(this);
        registerMessageHandlers();
        initDummyData();
    }

    private void registerMessageHandlers() {
        messaging.registerMessage(WorldRequestMessage.class);
        messaging.registerHandler(WorldRequestMessage.class, worldRequestHandler);
        messaging.start();
    }

    private void initDummyData() {
        World world = newTestWorld();
        long start = System.currentTimeMillis();

        IntStream.range(0, world.getSpawnRadius()).forEach(x -> IntStream.range(0, world.getSpawnRadius()).forEach(z -> worldLoader.loadChunk(world, x, z)));

        log.info("Done? {}ms", (System.currentTimeMillis() - start));
    }

    private World newTestWorld() {
        return worldLoader.getWorld("test");
    }

    public static void main(String[] args) {
        new JungleWorldApplication();
    }
}
