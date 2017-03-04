package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class EntityInteractMessage implements Message {

    private final int targetEntityId;
    private final int interactType;
    private final float targetX;
    private final float targetY;
    private final float targetZ;
    private final int hand;

    public EntityInteractMessage(int targetEntityId, int interactType, float targetX, float targetY, float targetZ, int hand) {
        this.targetEntityId = targetEntityId;
        this.interactType = interactType;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.hand = hand;
    }

    public int getTargetEntityId() {
        return targetEntityId;
    }

    public int getInteractType() {
        return interactType;
    }

    public float getTargetX() {
        return targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public float getTargetZ() {
        return targetZ;
    }

    public int getHand() {
        return hand;
    }
}
