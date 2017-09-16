package org.jungletree.connector.mcj.jms;

import org.jungletree.connector.mcj.player.JunglePlayer;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.messaging.message.WorldRequestMessage;
import org.jungletree.rainforest.messaging.message.WorldResponseMessage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class WorldManager implements MessageHandler<WorldResponseMessage> {

    private Map<String, JunglePlayer> requests = new ConcurrentHashMap<>();

    private final MessagingService messaging;

    @Inject
    public WorldManager(MessagingService messaging) {
        this.messaging = messaging;
    }

    public void send(JunglePlayer player, WorldRequestMessage message) {
        String sender = player.getUniqueId().toString();
        message.setSender(sender);
        requests.put(sender, player);
        messaging.sendMessage(message);
    }

    @Override
    public void handle(WorldResponseMessage message) {
        String recipient = message.getRecipient();
        if (requests.containsKey(recipient)) {
            JunglePlayer player = requests.get(recipient);
            player.onWorldLoad(message.getWorld());
        }
    }
}
