package im.octo.jungletree.api.player;

import im.octo.jungletree.api.entity.Player;

public interface PlayerSession {

    boolean isOnline();

    Player getPlayer();
}
