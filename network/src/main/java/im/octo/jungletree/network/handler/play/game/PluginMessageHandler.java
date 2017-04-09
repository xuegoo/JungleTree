package im.octo.jungletree.network.handler.play.game;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.game.PluginMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class PluginMessageHandler implements MessageHandler<JSession, PluginMessage> {

    private static final Logger log = LoggerFactory.getLogger(PluginMessageHandler.class);
    @Override
    public void handle(JSession session, PluginMessage message) {
        final String channel = message.getChannel();
        final String messageData = new String(message.getMessage(), StandardCharsets.UTF_8);
        log.info("Message on plugin channel: {}", messageData);

        if (message.getChannel().equals("REGISTER")) {
            for (String registeredChannel : messageData.split("\0")) {
                log.info("\"{}\" registered channel: {}", session, registeredChannel);
                session.getPlayer().addChannel(registeredChannel);
            }
        } else if (message.getChannel().equals("UNREGISTER")) {
            for (String unregisteredChannel : messageData.split("\0")) {
                log.info("\"{}\" unregistered channel: {}", session, unregisteredChannel);
                session.getPlayer().removeChannel(unregisteredChannel);
            }
        } else if (channel.startsWith("MC|")) {
            log.info("MC message: {}", messageData);
            handleInternalChannel(session, channel, message.getMessage());
        } else {
            log.info("Other message: {}", messageData);
            // TODO: Client -> Server plugin messaging
            // Rainforest.getServer().getMessenger().dispatchIncomingMessage(session.getPlayer(), channel, message.getMessage());
        }
    }

    private void handleInternalChannel(JSession session, String channel, byte... data) {
        // TODO: Handle internal
    }
}
