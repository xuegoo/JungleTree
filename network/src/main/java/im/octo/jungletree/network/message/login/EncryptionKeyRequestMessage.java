package im.octo.jungletree.network.message.login;

import com.flowpowered.network.Message;

import java.util.Arrays;
import java.util.Objects;

public class EncryptionKeyRequestMessage implements Message {

    private final String serverId;
    private final byte[] publicKey;
    private final byte[] verifyToken;

    public EncryptionKeyRequestMessage(String serverId, byte[] publicKey, byte[] verifyToken) {
        this.serverId = serverId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    public String getServerId() {
        return serverId;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptionKeyRequestMessage that = (EncryptionKeyRequestMessage) o;
        return Objects.equals(serverId, that.serverId) &&
                Arrays.equals(publicKey, that.publicKey) &&
                Arrays.equals(verifyToken, that.verifyToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, publicKey, verifyToken);
    }
}
