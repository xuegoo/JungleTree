package org.jungletree.world;

import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.world.World;
import org.jungletree.rainforest.world.WorldLoader;
import org.jungletree.rainforest.world.messages.WorldRequestMessage;
import org.jungletree.rainforest.world.messages.WorldResponseMessage;
import org.jungletree.world.messaging.WorldRequestMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.stream.IntStream;

public class JungleWorldApplication {

    private static final Logger log = LoggerFactory.getLogger(JungleWorldApplication.class);

    private final MessagingService messaging;

    private final WorldLoader worldLoader;
    private final WorldRequestMessageHandler worldRequestHandler;


    private JungleWorldApplication() {
        this.messaging = ServiceLoader.load(MessagingService.class).findFirst().orElseThrow(NoSuchElementException::new);
        this.worldLoader = ServiceLoader.load(WorldLoader.class).findFirst().orElseThrow(NoSuchElementException::new);
        this.worldRequestHandler = ServiceLoader.load(WorldRequestMessageHandler.class).findFirst().orElseThrow(NoSuchElementException::new);

        registerMessageHandlers();
        initDummyData();
    }

    private void registerMessageHandlers() {
        messaging.start();
        messaging.registerMessage(WorldRequestMessage.class);
        messaging.registerHandler(WorldRequestMessage.class, worldRequestHandler);
        messaging.registerMessage(WorldResponseMessage.class);
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
