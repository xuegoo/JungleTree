package org.jungletree.connector.mcj.protocol;

import org.jungletree.connector.mcj.codec.handshake.HandshakeCodec;
import org.jungletree.connector.mcj.handler.handshake.HandshakeHandler;
import org.jungletree.connector.mcj.message.handshake.HandshakeMessage;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.scheduler.SchedulerService;

import javax.inject.Inject;

public class HandshakeProtocol extends JProtocol {

    @Inject
    public HandshakeProtocol(ClientConnectorResourceService resource, SchedulerService scheduler) {
        super("HANDSHAKE", 0, resource, scheduler);

        inbound(0x00, HandshakeMessage.class, HandshakeCodec.class, HandshakeHandler.class);
    }
}
