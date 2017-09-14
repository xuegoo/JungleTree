package org.jungletree.connector.mcj.protocol;

import org.jungletree.connector.mcj.codec.handshake.HandshakeCodec;
import org.jungletree.connector.mcj.handler.handshake.HandshakeHandler;
import org.jungletree.connector.mcj.message.handshake.HandshakeMessage;
import org.jungletree.network.ClientConnectorResourceService;

import javax.inject.Inject;

public class HandshakeProtocol extends JProtocol {

    @Inject
    public HandshakeProtocol(ClientConnectorResourceService resource) {
        super("HANDSHAKE", 0, resource);

        inbound(0x00, HandshakeMessage.class, HandshakeCodec.class, HandshakeHandler.class);
    }
}
