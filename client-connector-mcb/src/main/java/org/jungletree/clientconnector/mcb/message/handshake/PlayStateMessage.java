package org.jungletree.clientconnector.mcb.message.handshake;

import org.jungletree.clientconnector.mcb.PlayState;
import org.jungletree.clientconnector.mcb.message.Message;

import java.util.Objects;

public class PlayStateMessage implements Message {

    private byte senderSubClientId;
    private byte targetSubClientId;
    private PlayState playState;

    @Override
    public byte getId() {
        return 0x02;
    }

    @Override
    public byte getSenderSubClientId() {
        return senderSubClientId;
    }

    @Override
    public void setSenderSubClientId(byte senderSubClientId) {
        this.senderSubClientId = senderSubClientId;
    }

    @Override
    public byte getTargetSubClientId() {
        return targetSubClientId;
    }

    @Override
    public void setTargetSubClientId(byte targetSubClientId) {
        this.targetSubClientId = targetSubClientId;
    }

    public PlayState getPlayState() {
        return playState;
    }

    public void setPlayState(PlayState playState) {
        this.playState = playState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayStateMessage that = (PlayStateMessage) o;
        return senderSubClientId == that.senderSubClientId &&
                targetSubClientId == that.targetSubClientId &&
                playState == that.playState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderSubClientId, targetSubClientId, playState);
    }

    @Override
    public String toString() {
        return "PlayStateMessage{" +
                "senderSubClientId=" + senderSubClientId +
                ", targetSubClientId=" + targetSubClientId +
                ", playState=" + playState +
                '}';
    }
}
