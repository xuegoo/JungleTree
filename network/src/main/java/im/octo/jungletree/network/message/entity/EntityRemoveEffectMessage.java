package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

public class EntityRemoveEffectMessage implements Message {

    private final int id;
    private final int effect;

    public EntityRemoveEffectMessage(int id, int effect) {
        this.id = id;
        this.effect = effect;
    }

    public int getId() {
        return id;
    }

    public int getEffect() {
        return effect;
    }
}
