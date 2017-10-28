package org.jungletree.clientconnector.mcb.packet.handshake;

import org.jungletree.clientconnector.mcb.packet.SubClientPacket;

import java.util.Objects;

public class LoginPacket extends SubClientPacket {

    private int clientNetworkVersion;
    private ConnectionInfo connectionInfo;

    @Override
    public byte getId() {
        return 0x01;
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
        if (!super.equals(o)) return false;
        LoginPacket that = (LoginPacket) o;
        return clientNetworkVersion == that.clientNetworkVersion &&
                Objects.equals(connectionInfo, that.connectionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clientNetworkVersion, connectionInfo);
    }

    @Override
    public String toString() {
        return "LoginPacket{" +
                "clientNetworkVersion=" + clientNetworkVersion +
                ", connectionInfo=" + connectionInfo +
                "} " + super.toString();
    }
}
