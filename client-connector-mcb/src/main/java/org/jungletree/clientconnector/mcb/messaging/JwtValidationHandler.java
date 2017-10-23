package org.jungletree.clientconnector.mcb.messaging;

import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.PlayState;
import org.jungletree.clientconnector.mcb.message.handshake.PlayStateMessage;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackInfoMessage;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackStackMessage;
import org.jungletree.rainforest.auth.messages.GetServerTokenMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.util.Crypto;
import org.jungletree.rainforest.util.Messengers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class JwtValidationHandler implements MessageHandler<JwtAuthReponseMessage> {

    private static final Logger log = LoggerFactory.getLogger(JwtValidationHandler.class);

    // Request ID, ClientConnection
    private final Map<UUID, ClientConnection> pendingResponses = new ConcurrentHashMap<>();

    private final MessagingService messagingService;
    private final GetServerTokenHandler serverTokenHandler;

    public JwtValidationHandler(MessagingService messagingService) {
        this.messagingService = messagingService;
        messagingService.registerHandler(JwtAuthReponseMessage.class, this);

        this.serverTokenHandler = new GetServerTokenHandler(messagingService);
    }

    public Map<UUID, ClientConnection> getPendingResponses() {
        return pendingResponses;
    }

    @Override
    public void handle(JwtAuthReponseMessage message) {
        if (!message.getRecipient().equals(Messengers.CLIENT_CONNECTOR)) {
            return;
        }

        ClientConnection client = pendingResponses.remove(message.getLoginRequestId());
        JwtAuthReponseMessage.AuthenticationStatus status = message.getStatus();

        if (status.equals(JwtAuthReponseMessage.AuthenticationStatus.OK)) {
            updateProfileInfo(client, message);
            enableProtocolEncryption(client, message.getLoginRequestId(), message.getClientPublicKey());
            // sendResourcePackInfo(client);
        } else {
            log.info("Disconnecting: {}", status.toString());
            client.getConnection().disconnect(status.toString());
        }
    }

    private void enableProtocolEncryption(ClientConnection client, UUID loginRequestId, String clientPublicKey) {
        client.getProtocolEncryption().setClientPublicKey(Crypto.getECX509PublicKey(clientPublicKey));

        GetServerTokenMessage message = new GetServerTokenMessage();
        message.setSender(Messengers.CLIENT_CONNECTOR);
        message.setRecipient(Messengers.AUTHENTICATION);
        message.setLoginRequestId(loginRequestId);

        serverTokenHandler.getPendingResponses().put(loginRequestId, client);
        messagingService.sendMessage(message);
    }

    private void sendResourcePackInfo(ClientConnection client) {
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
