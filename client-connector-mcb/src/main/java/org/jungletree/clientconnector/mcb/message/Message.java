package org.jungletree.clientconnector.mcb.message;

public interface Message {

    byte getId();

    byte getSenderSubClientId();

    void setSenderSubClientId(byte senderSubClientId);

    byte getTargetSubClientId();

    void setTargetSubClientId(byte targetSubClientId);
}
