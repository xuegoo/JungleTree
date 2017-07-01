package im.octo.jungletree.network.handler.play;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.player.ClientSettingsMessage;
import im.octo.jungletree.network.metadata.JClientSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientSettingsHandler implements MessageHandler<JSession, ClientSettingsMessage> {

    private static final Logger log = LoggerFactory.getLogger(ClientSettingsHandler.class);

    @Override
    public void handle(JSession session, ClientSettingsMessage message) {
        log.info("Client settings received");
        session.getPlayer().setClientSettings(new JClientSettings(message));
    }
}
