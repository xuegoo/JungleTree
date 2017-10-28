package org.jungletree.clientconnector.mcb.packet.handshake;

import org.jungletree.clientconnector.mcb.packet.Packet;

import java.util.Objects;

public class ClientToServerHandshakePacket implements Packet {

    private byte senderSubClientId;
    private byte targetSubClientId;

    @Override
    public byte getId() {
        return 0x04;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientToServerHandshakePacket that = (ClientToServerHandshakePacket) o;
        return senderSubClientId == that.senderSubClientId &&
                targetSubClientId == that.targetSubClientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderSubClientId, targetSubClientId);
    }

    @Override
    public String toString() {
        return "ClientToServerHandshakePacket{" +
                "senderSubClientId=" + senderSubClientId +
                ", targetSubClientId=" + targetSubClientId +
                '}';
    }
}
