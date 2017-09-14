package org.jungletree.connector.mcj.message.login;

import com.flowpowered.network.AsyncableMessage;

public class EncryptionKeyResponseMessage implements AsyncableMessage {

    private final byte[] sharedSecret;
    private final byte[] verifyToken;

    public EncryptionKeyResponseMessage(byte[] sharedSecret, byte[] verifyToken) {
        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }
}
