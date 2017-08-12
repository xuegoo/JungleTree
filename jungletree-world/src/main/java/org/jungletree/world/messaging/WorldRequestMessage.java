package org.jungletree.world.messaging;

import org.jungletree.rainforest.messaging.Message;
import org.jungletree.world.JungleWorldApplication;

import java.util.Objects;

public class WorldRequestMessage implements Message {

    private String sender;
    private String worldName;

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public String getRecipient() {
        return JungleWorldApplication.MESSENGER_NAME;
    }

    public String getWorldName() {
        return worldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldRequestMessage that = (WorldRequestMessage) o;
        return Objects.equals(sender, that.sender) &&
                Objects.equals(worldName, that.worldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, worldName);
    }
}
