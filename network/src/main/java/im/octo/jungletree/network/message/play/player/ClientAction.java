package im.octo.jungletree.network.message.play.player;

import java.util.Optional;

public enum ClientAction {

    RESPAWN(0),
    REQUEST_STATISTICS(1),
    OPEN_INVENTORY(2);

    private final int actionId;

    ClientAction(int actionId) {
        this.actionId = actionId;
    }

    public int getActionId() {
        return actionId;
    }

    public static Optional<ClientAction> fromId(int actionId) {
        for (ClientAction a : values()) {
            if (a.actionId == actionId) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }
}
