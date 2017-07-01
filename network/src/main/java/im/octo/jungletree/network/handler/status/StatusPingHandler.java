package im.octo.jungletree.network.handler.status;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.status.StatusPingMessage;

public class StatusPingHandler implements MessageHandler<JSession, StatusPingMessage> {

    @Override
    public void handle(JSession session, StatusPingMessage message) {
        session.send(message);
    }
}
