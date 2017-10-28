module jungletree.world {
    requires rainforest.messaging;
    requires rainforest.world;
    requires rainforest.util;
    requires rainforest.storage;
    requires rainforest.scheduler;

    requires jungletree.messenger;
    requires jungletree.scheduler;
    requires jungletree.storage;

    requires org.slf4j;
    requires gson;
    requires redisson;

    uses org.jungletree.rainforest.scheduler.SchedulerService;
    uses org.jungletree.rainforest.messaging.MessagingService;
    uses org.jungletree.rainforest.storage.StorageService;

    provides org.jungletree.rainforest.world.Chunk with org.jungletree.world.JungleChunk;
    provides org.jungletree.rainforest.world.Block with org.jungletree.world.JungleBlock;

    provides org.jungletree.rainforest.world.WorldService with org.jungletree.world.JungleWorldService;
    uses org.jungletree.rainforest.world.WorldService;

    provides org.jungletree.rainforest.world.WorldLoader with org.jungletree.world.JungleWorldLoader;
    uses org.jungletree.rainforest.world.WorldLoader;

    provides org.jungletree.world.messaging.WorldRequestMessageHandler with org.jungletree.world.messaging.WorldRequestMessageHandler;
    uses org.jungletree.world.messaging.WorldRequestMessageHandler;

}
