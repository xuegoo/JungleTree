package org.jungletree.clientconnector.mcb.packet.handshake;

import org.jungletree.clientconnector.mcb.packet.Packet;

import java.util.Objects;

public class ServerToClientHandshakePacket implements Packet {

    private byte senderSubClientId;
    private byte targetSubClientId;
    private String serverToken;

    @Override
    public byte getId() {
        return 0x03;
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

    public String getServerToken() {
        return serverToken;
    }

    public void setServerToken(String serverToken) {
        this.serverToken = serverToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerToClientHandshakePacket that = (ServerToClientHandshakePacket) o;
        return senderSubClientId == that.senderSubClientId &&
                targetSubClientId == that.targetSubClientId &&
                Objects.equals(serverToken, that.serverToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderSubClientId, targetSubClientId, serverToken);
    }

    @Override
    public String toString() {
        return "ServerToClientHandshakePacket{" +
                "senderSubClientId=" + senderSubClientId +
                ", targetSubClientId=" + targetSubClientId +
                ", serverToken='" + serverToken + '\'' +
                '}';
    }
}
