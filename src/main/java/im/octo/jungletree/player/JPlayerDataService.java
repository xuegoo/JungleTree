package im.octo.jungletree.player;

import com.google.inject.Singleton;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.player.OfflinePlayer;
import im.octo.jungletree.api.player.PlayerDataService;
import im.octo.jungletree.api.player.PlayerSession;
import im.octo.jungletree.api.player.meta.PlayerProfile;
import im.octo.jungletree.api.world.Dimension;
import im.octo.jungletree.api.world.Location;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.PositionRotationMessage;
import im.octo.jungletree.network.message.play.player.JoinGameMessage;
import im.octo.jungletree.network.message.play.player.PlayerAbilitiesMessage;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class JPlayerDataService implements PlayerDataService {

    private final Map<String, UUID> uuidCache = new ConcurrentHashMap<>();

    @Override
    public PlayerReader beginReadingData(UUID uuid) {
        return new JPlayerReader(uuid);
    }

    @Override
    public void readData(Player player) {
    }

    @Override
    public void writeData(Player player) {
    }

    @Override
    public Collection<OfflinePlayer> getOfflinePlayers() {
        return null;
    }

    @Override
    public UUID lookupUuid(String username) {
        return null;
    }

    @Override
    public Player initializePlayer(PlayerSession session, PlayerProfile profile, PlayerReader reader) {
        return new JPlayer(session, profile, reader);
    }

    @Override
    public void join(PlayerSession playerSession, PlayerReader playerReader, Player player) {
        Dimension dimension = player.getWorld().getDimension();
        JSession session = (JSession) player.getSession();
        session.send(new JoinGameMessage(
                0,
                0,
                dimension.getId(),
                0,
                0,
                "default",
                false
        ));

        // TODO: Implement world spawn location
        // player.setCompassTarget(player.getWorld().getSpawnLocation());

        Location location = player.getLocation();
        session.send(new PositionRotationMessage(location));
    }

    private void sendAbilities(PlayerSession playerSession, PlayerReader reader, Player player) {
        boolean creative = false;
        boolean canFly = false;
        boolean flying = false;
        float flySpeed = 0f;
        float walkSpeed = 1f;
        int flags = (creative ? 8 : 0) | (canFly ? 4 : 0) | (flying ? 2 : 0) | (creative ? 1 : 0);


        JSession session = (JSession) playerSession;

        // division is conversion from Bukkit to MC units
        session.send(new PlayerAbilitiesMessage(flags, flySpeed / 2f, walkSpeed / 2f));
    }

    public class JPlayerReader implements PlayerReader {

        private final UUID uuid;

        public JPlayerReader(UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public boolean hasPlayedBefore() {
            return false;
        }

        @Override
        public Location getLocation() {
            return null;
        }

        @Override
        public Location getBedSpawnLocation() {
            return null;
        }

        @Override
        public long getFirstPlayed() {
            return 0;
        }

        @Override
        public long getLastPlayed() {
            return 0;
        }

        @Override
        public String getLastKnownUsername() {
            return null;
        }

        @Override
        public void readData(Player player) {
        }

        @Override
        public void close() {
        }
    }
}
