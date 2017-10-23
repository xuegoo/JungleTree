package org.jungletree.clientconnector.mcb.message.crypto;

import org.jungletree.clientconnector.mcb.message.Message;

import java.util.Objects;

public class ClientToServerHandshakeMessage implements Message {

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
}
