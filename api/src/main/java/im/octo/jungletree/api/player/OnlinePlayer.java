package im.octo.jungletree.api.player;

import im.octo.jungletree.api.metadata.ClientSettings;

import java.util.UUID;

public interface OnlinePlayer extends OfflinePlayer {

    default boolean isOnline() {
        return true;
    }

    String getName();

    UUID getUuid();

    PlayerSession getSession();

    ClientSettings getClientSettings();

    void setClientSettings(ClientSettings clientSettings);
}
