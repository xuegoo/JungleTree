package org.jungletree.connector.mcj.handler.status;

import com.flowpowered.network.MessageHandler;
import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.message.status.StatusPingMessage;

public class StatusPingHandler implements MessageHandler<JSession, StatusPingMessage> {

    @Override
    public void handle(JSession session, StatusPingMessage message) {
        session.send(message);
    }
}
