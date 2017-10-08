package org.jungletree.world.messaging;

import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.messaging.message.WorldRequestMessage;
import org.jungletree.rainforest.messaging.message.WorldResponseMessage;
import org.jungletree.rainforest.util.Messengers;
import org.jungletree.rainforest.world.World;
import org.jungletree.rainforest.world.WorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WorldRequestMessageHandler implements MessageHandler<WorldRequestMessage> {

    private static final Logger log = LoggerFactory.getLogger(WorldRequestMessageHandler.class);

    private final MessagingService messaging;
    private final WorldService worldService;

    @Inject
    public WorldRequestMessageHandler(MessagingService messaging, WorldService worldService) {
        this.messaging = messaging;
        this.worldService = worldService;
    }

    @Override
    public void handle(WorldRequestMessage message) {
        String sender = message.getSender();

        log.info("Got world request from {}", sender);

        String worldName = message.getWorldName();
        World world = worldService.getWorld(worldName);

        WorldResponseMessage response = new WorldResponseMessage();
        response.setSender(Messengers.WORLD_STORAGE);
        response.setRecipient(sender);
        response.setWorld(world);

        log.info("Sending world to {}", sender);

        messaging.sendMessage(response);
    }
}
