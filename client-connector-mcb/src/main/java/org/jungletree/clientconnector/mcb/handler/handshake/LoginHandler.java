package org.jungletree.clientconnector.mcb.handler.handshake;

import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.PlayState;
import org.jungletree.clientconnector.mcb.crypto.ProtocolEncryption;
import org.jungletree.clientconnector.mcb.handler.MessageHandler;
import org.jungletree.clientconnector.mcb.message.handshake.LoginMessage;
import org.jungletree.clientconnector.mcb.message.handshake.PlayStateMessage;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackInfoMessage;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackStackMessage;
import org.jungletree.clientconnector.mcb.messaging.GetServerTokenHandler;
import org.jungletree.clientconnector.mcb.messaging.JwtValidationHandler;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage.AuthenticationStatus;
import org.jungletree.rainforest.auth.messages.JwtAuthRequestMessage;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.util.Crypto;
import org.jungletree.rainforest.util.Messengers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LoginHandler implements MessageHandler<LoginMessage> {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    private final MessagingService messaging;

    private final JwtValidationHandler validationHandler;

    public LoginHandler(MessagingService messaging) {
        this.messaging = messaging;
        this.validationHandler = new JwtValidationHandler(messaging);
    }

    @Override
    public void handle(ClientConnection client, LoginMessage message) {
        log.info("Got login request: {}", message.toString());
        String chain[] = message.getConnectionInfo().getTokenChain().getChain();
        String token = message.getConnectionInfo().getClientDataToken();

        UUID requestId = UUID.randomUUID();

        JwtAuthRequestMessage validationRequest = new JwtAuthRequestMessage();
        validationRequest.setSender(Messengers.CLIENT_CONNECTOR);
        validationRequest.setRecipient(Messengers.AUTHENTICATION);
        validationRequest.setLoginRequestId(requestId);
        validationRequest.setChain(chain);
        validationRequest.setJwtToken(token);
        messaging.sendMessage(validationRequest);

        validationHandler.getPendingResponses().put(requestId, client);
    }
}
