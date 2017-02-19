package im.octo.jungletree.api.event.type.player;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.event.Event;

public class PlayerEvent implements Event {

    private final Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return PlayerEvent.class.getSimpleName();
    }

    @Override
    public Type getType() {
        return Type.PLAYER;
    }

    public final Player getPlayer() {
        return player;
    }
}
