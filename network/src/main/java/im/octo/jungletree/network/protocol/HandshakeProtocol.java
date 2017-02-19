package im.octo.jungletree.network.protocol;

import im.octo.jungletree.network.codec.handshake.HandshakeCodec;
import im.octo.jungletree.network.handler.handshake.HandshakeHandler;
import im.octo.jungletree.network.message.handshake.HandshakeMessage;

public class HandshakeProtocol extends JProtocol {

    public HandshakeProtocol() {
        super(ProtocolType.HANDSHAKE.name(), 0);
        inbound(0x00, HandshakeMessage.class, HandshakeCodec.class, HandshakeHandler.class);
    }
}
