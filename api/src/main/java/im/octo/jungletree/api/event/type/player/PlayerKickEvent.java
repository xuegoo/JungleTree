package im.octo.jungletree.api.event.type.player;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.event.Cancellable;

public class PlayerKickEvent extends PlayerEvent implements Cancellable {

    private String kickReason;
    private String leaveMessage;
    private boolean cancelled;

    public PlayerKickEvent(Player player, String kickReason) {
        this(player, kickReason, null);
    }

    public PlayerKickEvent(Player player, String kickReason, String leaveMessage) {
        super(player);
        this.kickReason = kickReason;
        this.leaveMessage = leaveMessage;
        this.cancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getKickReason() {
        return kickReason;
    }

    public void setKickReason(String kickReason) {
        this.kickReason = kickReason;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
}
