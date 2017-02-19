package network.protocol;

import network.codec.handshake.HandshakeCodec;
import network.handler.handshake.HandshakeHandler;
import network.message.handshake.HandshakeMessage;

public class HandshakeProtocol extends JProtocol {

    public HandshakeProtocol() {
        super(ProtocolType.HANDSHAKE.name(), 0);
        inbound(0x00, HandshakeMessage.class, HandshakeCodec.class, HandshakeHandler.class);
    }
}
