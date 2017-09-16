package org.jungletree.connector.mcj.handler.play.game;

import com.flowpowered.network.MessageHandler;
import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.message.play.game.KeepAliveMessage;

public class KeepAliveHandler implements MessageHandler<JSession, KeepAliveMessage> {

    @Override
    public void handle(JSession session, KeepAliveMessage message) {
        session.send(message);
    }
}
