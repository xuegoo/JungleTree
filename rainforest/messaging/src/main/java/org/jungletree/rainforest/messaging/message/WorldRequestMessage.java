package org.jungletree.rainforest.messaging.message;

import org.jungletree.rainforest.messaging.Message;

import java.util.Objects;

public class WorldRequestMessage implements Message {

    private String sender;
    private String recipient;
    private String worldName;

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

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldRequestMessage that = (WorldRequestMessage) o;
        return Objects.equals(sender, that.sender) &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(worldName, that.worldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, worldName);
    }
}
