package org.jungletree.connector.mcj.protocol;

public enum ProtocolType {

    HANDSHAKE,
    STATUS,
    LOGIN,
    PLAY;

    private JProtocol protocol;

    ProtocolType() {}

    public JProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(JProtocol protocol) {
        this.protocol = protocol;
    }
}
