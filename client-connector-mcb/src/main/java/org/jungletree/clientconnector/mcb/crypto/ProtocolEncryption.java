package org.jungletree.clientconnector.mcb.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.interfaces.ECPublicKey;

public class ProtocolEncryption {

    private static final Logger log = LoggerFactory.getLogger(ProtocolEncryption.class);

    private ECPublicKey clientPublicKey;

    private byte[] salt;
    private byte[] key;

    public ECPublicKey getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(ECPublicKey clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }
}
