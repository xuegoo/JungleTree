package org.jungletree.clientconnector.mcb.packet;

public interface Packet {

    byte getId();

    byte getSenderSubClientId();

    void setSenderSubClientId(byte senderSubClientId);

    byte getTargetSubClientId();

    void setTargetSubClientId(byte targetSubClientId);
}
