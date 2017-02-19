package im.octo.jungletree.network.handler.login;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.login.LoginStartMessage;

public class LoginStartHandler implements MessageHandler<JSession, LoginStartMessage> {

    @Override
    public void handle(JSession session, LoginStartMessage message) {
        Server server = Rainforest.getServer();
        String username = message.getUsername();

        if (server.isOnlineMode()) {
            String sessionId = session.getSessionId();
        }
    }
}
