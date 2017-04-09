package im.octo.jungletree.network.handler.play.game;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.player.IncomingChatMessage;

public class IncomingChatHandler implements MessageHandler<JSession, IncomingChatMessage> {

    @Override
    public void handle(JSession session, IncomingChatMessage message) {
        if (!session.isOnline()) {
            return;
        }

        if (!message.getMessage().isEmpty()) {
            session.getPlayer().chat(message.getMessage());
        }
    }
}
