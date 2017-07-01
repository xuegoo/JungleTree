package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;
import im.octo.jungletree.rainforest.minecraft.entity.EntityEffect;

public class EntityStatusMessage implements Message {

    private final int id;
    private final int status;

    public EntityStatusMessage(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public EntityStatusMessage(int id, EntityEffect entityEffect) {
        this(id, entityEffect.getId());
    }
}
