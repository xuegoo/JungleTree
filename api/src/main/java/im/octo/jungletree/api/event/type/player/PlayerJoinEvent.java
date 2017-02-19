package im.octo.jungletree.api.event.type.player;

import im.octo.jungletree.api.entity.Player;

public class PlayerJoinEvent extends PlayerEvent {

    private String joinMessage;

    public PlayerJoinEvent(Player player) {
        this(player, null);
    }

    public PlayerJoinEvent(Player player, String joinMessage) {
        super(player);
        this.joinMessage = joinMessage;
    }

    @Override
    public String getName() {
        return PlayerJoinEvent.class.getSimpleName();
    }

    public String getJoinMessage() {
        return joinMessage;
    }
}
