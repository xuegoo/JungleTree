package org.jungletree.clientconnector.mcb.messaging;

import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.packet.crypto.ServerToClientHandshakePacket;
import org.jungletree.rainforest.auth.messages.GetServerTokenMessage;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.util.Messengers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GetServerTokenHandler implements MessageHandler<GetServerTokenMessage> {

    private static final Logger log = LoggerFactory.getLogger(GetServerTokenHandler.class);

    // Request ID, ClientConnection
    private final Map<UUID, ClientConnection> pendingResponses = new ConcurrentHashMap<>();

    private final MessagingService messagingService;

    public GetServerTokenHandler(MessagingService messagingService) {
        this.messagingService = messagingService;
        messagingService.registerHandler(GetServerTokenMessage.class, this);
    }

    public Map<UUID, ClientConnection> getPendingResponses() {
        return pendingResponses;
    }

    @Override
    public void handle(GetServerTokenMessage message) {
        if (!message.getRecipient().equals(Messengers.CLIENT_CONNECTOR)) {
            return;
        }

        log.info("Got server token response from authenticator");
        ClientConnection client = pendingResponses.remove(message.getLoginRequestId());
        String serverToken = message.getServerToken();

        ServerToClientHandshakePacket handshake = new ServerToClientHandshakePacket();
        handshake.setServerToken(serverToken);
        client.setEncryptionEnabled(true);
        client.send(handshake);
    }
}
