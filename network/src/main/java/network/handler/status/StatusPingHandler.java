package network.handler.status;

import com.flowpowered.network.MessageHandler;
import network.JSession;
import network.message.status.StatusPingMessage;

public class StatusPingHandler implements MessageHandler<JSession, StatusPingMessage> {

    @Override
    public void handle(JSession session, StatusPingMessage message) {
        session.send(message);
    }
}
