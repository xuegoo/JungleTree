package im.octo.jungletree.network.handler.play;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.player.TeleportConfirmMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeleportConfirmHandler implements MessageHandler<JSession, TeleportConfirmMessage> {

    private static final Logger log = LoggerFactory.getLogger(TeleportConfirmHandler.class);

    @Override
    public void handle(JSession session, TeleportConfirmMessage message) {
        log.info("Entity with the ID \"{}\" teleported", message.getTeleportId());
        //TODO: Handle this
    }
}
