package im.octo.jungletree.network.handler.login;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.type.player.AsyncPlayerPreLoginEvent;
import im.octo.jungletree.rainforest.network.LoginResult;
import im.octo.jungletree.api.player.meta.PlayerProfile;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.SecurityUtils;
import im.octo.jungletree.network.message.login.EncryptionKeyRequestMessage;
import im.octo.jungletree.network.message.login.LoginStartMessage;

public class LoginStartHandler implements MessageHandler<JSession, LoginStartMessage> {

    @Override
    public void handle(JSession session, LoginStartMessage message) {
        Server server = Rainforest.getServer();
        String username = message.getUsername();

        String sessionId = session.getSessionId();
        byte[] publicKey = SecurityUtils.generateX509Key(server.getKeyPair().getPublic()).getEncoded();
        byte[] verifyToken = SecurityUtils.generateVerifyToken();

        session.setVerifyUsername(username);
        session.setVerifyToken(verifyToken);
        session.send(new EncryptionKeyRequestMessage(sessionId, publicKey, verifyToken));
    }
}
