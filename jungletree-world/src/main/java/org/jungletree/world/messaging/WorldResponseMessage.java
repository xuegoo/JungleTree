package org.jungletree.world.messaging;

import org.jungletree.rainforest.messaging.Message;
import org.jungletree.world.JungleWorld;

public class WorldResponseMessage implements Message {

    private String sender;
    private String recipient;
    private JungleWorld world;

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

    public JungleWorld getWorld() {
        return world;
    }

    public void setWorld(JungleWorld world) {
        this.world = world;
    }
}
