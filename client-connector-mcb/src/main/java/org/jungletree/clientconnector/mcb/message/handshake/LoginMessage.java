package org.jungletree.clientconnector.mcb.message.handshake;

import org.jungletree.clientconnector.mcb.message.Message;

import java.util.Objects;

public class LoginMessage implements Message {

    private byte senderSubClientId;
    private byte targetSubClientId;
    private int clientNetworkVersion;
    private ConnectionInfo connectionInfo;

    @Override
    public byte getId() {
        return 0x01;
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

    public int getClientNetworkVersion() {
        return clientNetworkVersion;
    }

    public void setClientNetworkVersion(int clientNetworkVersion) {
        this.clientNetworkVersion = clientNetworkVersion;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginMessage message = (LoginMessage) o;
        return senderSubClientId == message.senderSubClientId &&
                targetSubClientId == message.targetSubClientId &&
                clientNetworkVersion == message.clientNetworkVersion &&
                Objects.equals(connectionInfo, message.connectionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderSubClientId, targetSubClientId, clientNetworkVersion, connectionInfo);
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "senderSubClientId=" + senderSubClientId +
                ", targetSubClientId=" + targetSubClientId +
                ", clientNetworkVersion=" + clientNetworkVersion +
                ", connectionInfo=" + connectionInfo +
                '}';
    }
}
