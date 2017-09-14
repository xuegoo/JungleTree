package org.jungletree.connector.mcj.protocol;

import org.jungletree.connector.mcj.codec.status.StatusPingCodec;
import org.jungletree.connector.mcj.codec.status.StatusRequestCodec;
import org.jungletree.connector.mcj.codec.status.StatusResponseCodec;
import org.jungletree.connector.mcj.handler.status.StatusRequestHandler;
import org.jungletree.connector.mcj.message.status.StatusResponseMessage;
import org.jungletree.connector.mcj.handler.status.StatusPingHandler;
import org.jungletree.connector.mcj.message.status.StatusPingMessage;
import org.jungletree.connector.mcj.message.status.StatusRequestMessage;
import org.jungletree.network.ClientConnectorResourceService;

import javax.inject.Inject;

public class StatusProtocol extends JProtocol {

    @Inject
    public StatusProtocol(ClientConnectorResourceService resource) {
        super("STATUS", 2, resource);

        inbound(0x00, StatusRequestMessage.class, StatusRequestCodec.class, StatusRequestHandler.class);
        inbound(0x01, StatusPingMessage.class, StatusPingCodec.class, StatusPingHandler.class);

        outbound(0x00, StatusResponseMessage.class, StatusResponseCodec.class);
        outbound(0x01, StatusPingMessage.class, StatusPingCodec.class);
    }
}
