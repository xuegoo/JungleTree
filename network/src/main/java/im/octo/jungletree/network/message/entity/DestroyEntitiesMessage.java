package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

import java.util.List;

public class DestroyEntitiesMessage implements Message {

    private final List<Integer> ids;

    public DestroyEntitiesMessage(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }
}
