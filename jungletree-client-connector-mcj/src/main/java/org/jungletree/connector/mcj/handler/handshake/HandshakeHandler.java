package org.jungletree.connector.mcj.handler.handshake;

import com.flowpowered.network.MessageHandler;
import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.message.handshake.HandshakeMessage;
import org.jungletree.connector.mcj.protocol.ProtocolType;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;

import javax.inject.Singleton;

@Singleton
public class HandshakeHandler implements MessageHandler<JSession, HandshakeMessage> {

    private final ClientConnectorResourceService resource;

    public HandshakeHandler(ClientConnectorResourceService resource) {
        this.resource = resource;
    }

    @Override
    public void handle(JSession session, HandshakeMessage message) {
        ProtocolType protocol;
        if (message.getState() == 1) {
            protocol = ProtocolType.STATUS;
        } else if (message.getState() == 2) {
            protocol = ProtocolType.LOGIN;
        } else {
            session.disconnect("Invalid state");
            return;
        }

        session.setVersion(message.getProtocolVersion());
        session.setHostname(message.getAddress() + ":" + message.getPort());

        session.setProtocol(protocol);

        if (protocol == ProtocolType.LOGIN) {
            int protocolVersion = resource.getProtocolVersion();
            if (message.getProtocolVersion() < protocolVersion) {
                session.disconnect("Outdated client! I'm running " + protocolVersion);
            } else if (message.getProtocolVersion() > protocolVersion) {
                session.disconnect("Outdated server! I'm running " + protocolVersion);
            }
        }
    }
}
