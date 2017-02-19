package network.message.entity;

import com.flowpowered.network.Message;

// TODO: Continue when properties are ready
public class EntityPropertyMessage implements Message {

    private final int id;
    // private final Map<String, Property> properties;


    public EntityPropertyMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
