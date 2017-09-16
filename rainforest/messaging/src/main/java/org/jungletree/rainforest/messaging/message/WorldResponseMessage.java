package org.jungletree.rainforest.messaging.message;

import org.jungletree.rainforest.messaging.Message;
import org.jungletree.rainforest.world.World;

public class WorldResponseMessage implements Message {

    private String sender;
    private String recipient;
    private World world;

    @Override
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
