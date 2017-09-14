package org.jungletree.connector.mcj.handler.login;

import com.flowpowered.network.MessageHandler;
import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.SecurityUtils;
import org.jungletree.connector.mcj.message.login.EncryptionKeyRequestMessage;
import org.jungletree.connector.mcj.message.login.LoginStartMessage;
import org.jungletree.network.ClientConnectorResourceService;

public class LoginStartHandler implements MessageHandler<JSession, LoginStartMessage> {

    private final ClientConnectorResourceService resource;

    public LoginStartHandler(ClientConnectorResourceService resource) {
        this.resource = resource;
    }

    @Override
    public void handle(JSession session, LoginStartMessage message) {
        String username = message.getUsername();

        String sessionId = session.getSessionId();
        byte[] publicKey = SecurityUtils.generateX509Key(resource.getKeyPair().getPublic()).getEncoded();
        byte[] verifyToken = SecurityUtils.generateVerifyToken();

        session.setVerifyUsername(username);
        session.setVerifyToken(verifyToken);
        session.send(new EncryptionKeyRequestMessage(sessionId, publicKey, verifyToken));
    }
}
