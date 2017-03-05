package im.octo.jungletree;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.player.OfflinePlayer;
import im.octo.jungletree.api.player.PlayerDataService;
import im.octo.jungletree.api.player.PlayerSession;
import im.octo.jungletree.api.player.meta.PlayerProfile;

import java.util.Collection;
import java.util.UUID;

public class JPlayerDataService implements PlayerDataService {

    @Override
    public PlayerReader beginReadingData(UUID uuid) {
        return null;
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
        return null;
    }

    @Override
    public void join(PlayerSession playerSession, PlayerReader playerReader, Player player) {
    }
}
