package org.jungletree.world.messaging;

import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.Messenger;
import org.jungletree.rainforest.messaging.MessengerService;
import org.jungletree.rainforest.util.Messengers;
import org.jungletree.rainforest.world.World;
import org.jungletree.rainforest.world.WorldService;
import org.jungletree.rainforest.world.messages.WorldRequestMessage;
import org.jungletree.rainforest.world.messages.WorldResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ServiceLoader;

public class WorldRequestMessageHandler implements MessageHandler<WorldRequestMessage> {

    private static final Logger log = LoggerFactory.getLogger(WorldRequestMessageHandler.class);

    private final Messenger messaging;
    private final WorldService worldService;

    public WorldRequestMessageHandler() {
        this.messaging = MessengerService.getInstance().getMessenger();
        this.worldService = loadWorldService();
    }

    private WorldService loadWorldService() {
        WorldService worldService;
        ServiceLoader<WorldService> messagingServiceLoader = ServiceLoader.load(WorldService.class);
        Optional<WorldService> worldServiceOptional = messagingServiceLoader.findFirst();

        if (worldServiceOptional.isPresent()) {
            worldService = worldServiceOptional.get();
        } else {
            throw new NoSuchElementException("World service not defined");
        }
        return worldService;
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
