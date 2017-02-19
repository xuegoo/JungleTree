package im.octo.jungletree.api.player;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.player.meta.PlayerProfile;
import im.octo.jungletree.api.world.Location;

import java.util.Collection;
import java.util.UUID;

public interface PlayerDataService {

    PlayerReader beginReadingData(UUID uuid);

    void readData(Player player);

    void writeData(Player player);

    Collection<OfflinePlayer> getOfflinePlayers();

    UUID lookupUuid(String username);

    Player initializePlayer(PlayerSession session, PlayerProfile profile, PlayerReader reader);

    void join(PlayerSession playerSession, PlayerReader playerReader, Player player);

    interface PlayerReader extends AutoCloseable {

        boolean hasPlayedBefore();

        Location getLocation();

        Location getBedSpawnLocation();

        long getFirstPlayed();

        long getLastPlayed();

        String getLastKnownUsername();

        void readData(Player player);

        @Override
        void close();
    }
}
