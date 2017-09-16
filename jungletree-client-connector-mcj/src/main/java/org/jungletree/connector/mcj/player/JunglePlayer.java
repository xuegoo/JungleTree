package org.jungletree.connector.mcj.player;

import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.handler.login.model.PlayerProfile;
import org.jungletree.connector.mcj.jms.WorldManager;
import org.jungletree.connector.mcj.message.play.game.JoinGameMessage;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.entity.Player;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.message.WorldRequestMessage;
import org.jungletree.rainforest.messaging.message.WorldResponseMessage;
import org.jungletree.rainforest.util.Messengers;
import org.jungletree.rainforest.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;

import static org.jungletree.connector.mcj.JungleMcjApplication.getInjector;
import static org.jungletree.rainforest.util.Messengers.CLIENT_CONNECTOR;
import static org.jungletree.rainforest.util.Messengers.WORLD_STORAGE;

public class JunglePlayer implements Player {

    private static final Logger log = LoggerFactory.getLogger(JunglePlayer.class);

    @Inject private ClientConnectorResourceService resource;
    @Inject private WorldManager worldManager;
    private PlayerProfile profile;

    private World world;
    private JSession session;
    private MessageHandler<WorldResponseMessage> worldHandler;

    private long joinTime;

    public JunglePlayer(PlayerProfile profile) {
        getInjector().injectMembers(this);

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

        requestWorld();
    }

    private void requestWorld() {
        log.trace("Requesting world for player {}", profile.getName());
        WorldRequestMessage worldRequestMessage = new WorldRequestMessage();
        worldRequestMessage.setWorldName("world");
        worldRequestMessage.setSender(Messengers.ident(CLIENT_CONNECTOR));
        worldRequestMessage.setRecipient(WORLD_STORAGE);
        worldManager.send(this, worldRequestMessage);
    }

    public void onWorldLoad(World world) {
        log.trace("Sending world info to player {}", profile.getName());
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
