package im.octo.jungletree.api.player;

import java.util.UUID;

public interface OfflinePlayer {

    default boolean isOnline() {
        return false;
    }

    String getName();

    UUID getUuid();
}
