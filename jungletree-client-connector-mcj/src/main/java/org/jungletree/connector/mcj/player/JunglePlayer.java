package org.jungletree.connector.mcj.player;

import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.handler.login.model.PlayerProfile;
import org.jungletree.connector.mcj.message.play.game.JoinGameMessage;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.entity.Player;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.messaging.message.WorldRequestMessage;
import org.jungletree.rainforest.messaging.message.WorldResponseMessage;
import org.jungletree.rainforest.util.Messengers;
import org.jungletree.rainforest.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.jungletree.rainforest.util.Messengers.CLIENT_CONNECTOR;
import static org.jungletree.rainforest.util.Messengers.WORLD_STORAGE;

public class JunglePlayer implements Player {

    private static final Logger log = LoggerFactory.getLogger(JunglePlayer.class);

    private final MessagingService messaging;
    private final ClientConnectorResourceService resource;
    private final PlayerProfile profile;

    private World world;
    private JSession session;
    private MessageHandler<WorldResponseMessage> worldHandler;

    private long joinTime;

    public JunglePlayer(MessagingService messaging, ClientConnectorResourceService resource, PlayerProfile profile) {
        this.messaging = messaging;
        this.resource = resource;
        this.profile = profile;
    }

    @Override
    public UUID getUniqueId() {
        return profile.getUniqueId();
    }

    @Override
    public String getName() {
        return profile.getName();
    }

    public void join(JSession session) {
        log.trace("Player {}: Session connected", profile.getName());
        this.session = session;

        WorldRequestMessage message = new WorldRequestMessage();
        message.setWorldName("world");
        message.setSender(Messengers.ident(CLIENT_CONNECTOR));
        message.setRecipient(WORLD_STORAGE);

        loadWorld(message);
    }

    private void loadWorld(WorldRequestMessage message) {
        log.trace("Loading world");
        this.worldHandler = response -> {
            // TODO: Bit of a hack to get this thing working, needs refactoring
            log.trace("Got world response!");
            if (!response.getRecipient().equals(message.getSender())) {
                return;
            }

            log.trace("Configuring world for player {}", profile.getName());
            JunglePlayer.this.onWorldLoaded(response.getWorld());

            // TODO: Again, a temporary message will stop this from being stupidly complicated
            if (worldHandler != null) {
                log.trace("Finishing up world loading...");
                messaging.unregisterHandler(WorldResponseMessage.class, worldHandler);
                JunglePlayer.this.worldHandler = null;
            }
        };
        messaging.registerHandler(WorldResponseMessage.class, worldHandler);

        log.trace("Sending world request through RabbitMQ...");
        messaging.sendMessage(message);
    }

    private void onWorldLoaded(World world) {
        this.world = world;
        session.send(new JoinGameMessage(
                world.getId(),
                world.getGameMode().getOrdinal(),
                world.getDimension().getOrdinal(),
                world.getDifficulty().getOrdinal(),
                resource.getMaxPlayers(),
                "flat",
                resource.isReducedDebugInfo()
        ));

        this.joinTime = System.currentTimeMillis();
    }

    public void quit() {
    }

    @Override
    public World getWorld() {
        return null;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
