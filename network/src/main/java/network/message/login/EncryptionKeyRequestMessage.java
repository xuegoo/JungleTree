package network.message.login;

import com.flowpowered.network.Message;

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
}
