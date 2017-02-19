package im.octo.jungletree.api.player;

import java.util.UUID;

public interface OnlinePlayer extends OfflinePlayer {

    default boolean isOnline() {
        return true;
    }

    String getName();

    UUID getUuid();

    PlayerSession getSession();
}
