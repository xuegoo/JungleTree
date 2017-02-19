package im.octo.jungletree.network.handler.handshake;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.ProxyData;
import im.octo.jungletree.network.message.handshake.HandshakeMessage;
import im.octo.jungletree.network.protocol.ProtocolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandshakeHandler implements MessageHandler<JSession, HandshakeMessage> {

    private static final Logger log = LoggerFactory.getLogger(HandshakeHandler.class);

    @Override
    public void handle(JSession session, HandshakeMessage message) {
        Server server = Rainforest.getServer();
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

        // Proxies modify the hostname in the HandshakeMessage to contain
        // the client's UUID and (optionally) properties
        if (server.getProxySupport()) {
            try {
                session.setProxyData(new ProxyData(session, message.getAddress()));
            } catch (IllegalArgumentException ex) {
                session.disconnect("Invalid proxy data provided.");
                // protocol is still set here and below to prevent errors
                // trying to decode packets after this one under the wrong
                // protocol, even though client is kicked
                session.setProtocol(protocol);
                return;
            } catch (Exception ex) {
                log.error("Error parsing proxy data for " + session, ex);
                session.disconnect("Failed to parse proxy data.");
                session.setProtocol(protocol);
                return;
            }
        }

        session.setProtocol(protocol);

        if (protocol == ProtocolType.LOGIN) {
            int protocolVersion = server.getGameVersion().getProtocolVersion();
            if (message.getProtocolVersion() < protocolVersion) {
                session.disconnect("Outdated client! I'm running " + protocolVersion);
            } else if (message.getProtocolVersion() > protocolVersion) {
                session.disconnect("Outdated server! I'm running " + protocolVersion);
            }
        }
    }
}
