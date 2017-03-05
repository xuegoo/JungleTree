package im.octo.jungletree.player;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.metadata.ClientSettings;
import im.octo.jungletree.api.player.PlayerDataService.PlayerReader;
import im.octo.jungletree.api.player.PlayerSession;
import im.octo.jungletree.api.player.meta.PlayerProfile;
import im.octo.jungletree.api.world.Location;
import im.octo.jungletree.api.world.World;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.player.SpawnPositionMessage;
import world.JungleWorld;

import java.util.UUID;

public class JPlayer implements Player {

    private final PlayerSession session;
    private final PlayerProfile profile;

    private Location location;
    private Location compassTarget;
    private boolean flying;
    private boolean flyingAllowed;
    private boolean grounded = true;
    private ClientSettings clientSettings;

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
    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    @Override
    public boolean isFlyingAllowed() {
        return flyingAllowed;
    }
}
