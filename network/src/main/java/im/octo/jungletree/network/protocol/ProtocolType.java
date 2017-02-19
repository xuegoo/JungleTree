package im.octo.jungletree.network.protocol;

public enum  ProtocolType {

    HANDSHAKE(new HandshakeProtocol()),
    STATUS(new StatusProtocol()),
    // LOGIN(new LoginProtocol()),
    // PLAY(new PlayProtocol())
    ;

    private final JProtocol protocol;

    ProtocolType(JProtocol protocol) {
        this.protocol = protocol;
    }

    public JProtocol getProtocol() {
        return protocol;
    }
}
