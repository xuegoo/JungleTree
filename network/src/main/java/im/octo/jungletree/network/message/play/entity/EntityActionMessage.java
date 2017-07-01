package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class EntityActionMessage implements Message {

    private final int playerId;
    private final int actionId;
    private final int jumpBoost;

    public EntityActionMessage(int playerId, int actionId, int jumpBoost) {
        this.playerId = playerId;
        this.actionId = actionId;
        this.jumpBoost = jumpBoost;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getActionId() {
        return actionId;
    }

    public int getJumpBoost() {
        return jumpBoost;
    }
}
