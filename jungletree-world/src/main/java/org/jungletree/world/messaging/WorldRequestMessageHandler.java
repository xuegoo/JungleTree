package org.jungletree.world.messaging;

import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.world.WorldService;
import org.jungletree.world.JungleWorld;
import org.jungletree.world.JungleWorldApplication;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WorldRequestMessageHandler implements MessageHandler<WorldRequestMessage> {

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
        String worldName = message.getWorldName();
        JungleWorld world = (JungleWorld) worldService.getWorld(worldName);

        WorldResponseMessage response = new WorldResponseMessage();
        response.setSender(JungleWorldApplication.MESSENGER_NAME);
        response.setRecipient(sender);
        response.setWorld(world);
        messaging.sendMessage(response);
    }
}
