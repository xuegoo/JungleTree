package im.octo.jungletree.player;

import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.type.player.PlayerRegisterChannelEvent;
import im.octo.jungletree.api.metadata.ClientSettings;
import im.octo.jungletree.api.player.PlayerDataService.PlayerReader;
import im.octo.jungletree.api.player.PlayerSession;
import im.octo.jungletree.api.player.meta.PlayerProfile;
import im.octo.jungletree.api.world.Location;
import im.octo.jungletree.api.world.World;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.player.SpawnPositionMessage;
import im.octo.jungletree.world.JungleWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JPlayer implements Player {

    private static final Logger log = LoggerFactory.getLogger(JPlayer.class);

    private final PlayerSession session;
    private final PlayerProfile profile;

    private Location location;
    private Location compassTarget;
    private boolean flying;
    private boolean flyingAllowed;
    private boolean grounded = true;
    private ClientSettings clientSettings;

    private final Set<String> listeningChannels = Collections.synchronizedSet(new HashSet<>());

    public JPlayer(PlayerSession session, PlayerProfile profile, PlayerReader reader) {
        this.session = session;
        this.profile = profile;

        this.location = new Location(new JungleWorld(), 0, 0, 0);
    }

    @Override
    public World getWorld() {
        return location.getWorld();
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public String getName() {
        return profile.getUsername();
    }

    @Override
    public UUID getUuid() {
        return profile.getUuid();
    }

    @Override
    public PlayerSession getSession() {
        return session;
    }

    @Override
    public ClientSettings getClientSettings() {
        return clientSettings;
    }

    @Override
    public void setClientSettings(ClientSettings clientSettings) {
        this.clientSettings = clientSettings;
    }

    @Override
    public void setCompassTarget(Location location) {
        this.compassTarget = location;
        ((JSession)session).send(new SpawnPositionMessage(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void kick(String reason) {
        ((JSession)session).disconnect(reason, true);
    }

    @Override
    public boolean isGrounded() {
        return grounded;
    }

    @Override
    public Collection<String> getListeningChannels() {
        return Collections.unmodifiableCollection(listeningChannels);
    }

    @Override
    public void addChannel(String channel) {
        if (listeningChannels.add(channel)) {
            EventService eventService = Rainforest.getServer().getGuice().getInstance(EventService.class);
            eventService.call(new PlayerRegisterChannelEvent(channel));
        }
    }

    @Override
    public void removeChannel(String channel) {
        if (listeningChannels.remove(channel)) {
            EventService eventService = Rainforest.getServer().getGuice().getInstance(EventService.class);
            eventService.call(new PlayerRegisterChannelEvent(channel));
        }
    }

    @Override
    public void chat(String message) {
        log.info("<{}>: {}", this.getName(), message);
    }

    @Override
    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    @Override
    public boolean isFlyingAllowed() {
        return flyingAllowed;
    }
}
