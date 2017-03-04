package im.octo.jungletree.network.message.play.game;

import com.flowpowered.network.Message;

public class AnimationMessage implements Message {

    private final int entityId;
    private final int animationId;

    public AnimationMessage(int entityId, int animationId) {
        this.entityId = entityId;
        this.animationId = animationId;
    }

    public int getEntityId() {
        return entityId;
    }

    public int getAnimationId() {
        return animationId;
    }
}
