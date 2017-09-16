package org.jungletree.connector.mcj.message.play.game;

import com.flowpowered.network.Message;

import java.util.Objects;

public class KeepAliveMessage implements Message {

    private final int pingId;

    public KeepAliveMessage(int pingId) {
        this.pingId = pingId;
    }

    public int getPingId() {
        return pingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeepAliveMessage that = (KeepAliveMessage) o;
        return pingId == that.pingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pingId);
    }
}
