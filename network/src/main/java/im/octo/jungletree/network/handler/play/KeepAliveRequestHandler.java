package im.octo.jungletree.network.handler.play;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.player.KeepAliveRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeepAliveRequestHandler implements MessageHandler<JSession, KeepAliveRequestMessage> {

    private static final Logger log = LoggerFactory.getLogger(KeepAliveRequestHandler.class);

    @Override
    public void handle(JSession session, KeepAliveRequestMessage message) {
        log.info("Ping!");
        session.pong(message.getKeepAliveId());
    }
}
