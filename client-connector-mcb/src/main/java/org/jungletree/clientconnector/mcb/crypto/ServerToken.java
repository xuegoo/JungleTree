package org.jungletree.clientconnector.mcb.crypto;

import java.util.Arrays;
import java.util.Objects;

public class ServerToken {

    private byte[] clientSalt;
    private String serverToken;

    public byte[] getClientSalt() {
        return clientSalt;
    }

    public void setClientSalt(byte[] clientSalt) {
        this.clientSalt = clientSalt;
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
        ServerToken token = (ServerToken) o;
        return Arrays.equals(clientSalt, token.clientSalt) &&
                Objects.equals(serverToken, token.serverToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientSalt, serverToken);
    }

    @Override
    public String toString() {
        return "ServerToken{" +
                "clientSalt=" + Arrays.toString(clientSalt) +
                ", serverToken='" + serverToken + '\'' +
                '}';
    }
}
