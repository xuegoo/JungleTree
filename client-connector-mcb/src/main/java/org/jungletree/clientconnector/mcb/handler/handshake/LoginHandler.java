package org.jungletree.clientconnector.mcb.handler.handshake;

import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.PlayState;
import org.jungletree.clientconnector.mcb.handler.MessageHandler;
import org.jungletree.clientconnector.mcb.message.handshake.LoginMessage;
import org.jungletree.clientconnector.mcb.message.handshake.PlayStateMessage;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackInfoMessage;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackStackMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage.AuthenticationStatus;
import org.jungletree.rainforest.auth.messages.JwtAuthRequestMessage;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.util.Messengers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LoginHandler implements MessageHandler<LoginMessage>, org.jungletree.rainforest.messaging.MessageHandler<JwtAuthReponseMessage> {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    // Request ID, ClientConnection
    private Map<UUID, ClientConnection> pendingResponses = new ConcurrentHashMap<>();

    private final MessagingService messaging;

    public LoginHandler(MessagingService messaging) {
        this.messaging = messaging;
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

        pendingResponses.put(requestId, client);
    }

    @Override
    public void handle(JwtAuthReponseMessage message) {
        if (!message.getRecipient().equals(Messengers.CLIENT_CONNECTOR)) {
            return;
        }

        ClientConnection client = pendingResponses.remove(message.getLoginRequestId());
        AuthenticationStatus status = message.getStatus();

        if (status.equals(AuthenticationStatus.OK)) {
            updateProfileInfo(client, message);

            PlayStateMessage playStateMessage = new PlayStateMessage();
            playStateMessage.setPlayState(PlayState.LOGIN_SUCCESS);

            client.send(playStateMessage);


            ResourcePackInfoMessage resourcePackInfoMessage = new ResourcePackInfoMessage();
            resourcePackInfoMessage.setMustAccept(false);
            resourcePackInfoMessage.setBehaviorPackInfo(Collections.emptyList());
            resourcePackInfoMessage.setResourcePackInfo(Collections.emptyList());

            ResourcePackStackMessage resourcePackStackMessage = new ResourcePackStackMessage();
            resourcePackStackMessage.setMustAccept(false);
            resourcePackStackMessage.setBehaviorPackIdVersions(Collections.emptyList());
            resourcePackStackMessage.setResourcePackIdVersions(Collections.emptyList());

            client.send(resourcePackInfoMessage);
            client.send(resourcePackStackMessage);
        } else {
            log.info("Disconnecting: {}", status.toString());
            client.getConnection().disconnect(status.toString());
        }
    }

    private void updateProfileInfo(ClientConnection client, JwtAuthReponseMessage message) {
        client.setClientRandomId(message.getClientRandomId());
        client.setDeviceModel(message.getDeviceModel());
        client.setDeviceOS(message.getDeviceOS());
        client.setGameVersion(message.getGameVersion());
        client.setLanguageCode(message.getLanguageCode());
        client.setCurrentInputMode(message.getCurrentInputMode());
        client.setDefaultInputMode(message.getDefaultInputMode());
        client.setGuiScale(message.getGuiScale());
        client.setSkinId(message.getSkinId());
        client.setSkinData(message.getSkinData());
        client.setSkinGeometryName(message.getSkinGeometryName());
        client.setUiProfile(message.getUiProfile());
    }
}
