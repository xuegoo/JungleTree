package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

// TODO: Continue when metadata is ready
public class EntityMetadataMessage implements Message {

    private final int id;
    // private final List<Entry> entries;

    public EntityMetadataMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
