package im.octo.jungletree.network.handler.play;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.player.PlayerAbilitiesMessage;

public class PlayerAbilitiesHandler implements MessageHandler<JSession, PlayerAbilitiesMessage> {

    @Override
    public void handle(JSession session, PlayerAbilitiesMessage message) {
        // player sends this when changing whether or not they are currently flying
        // other values should match what we've sent in the past but are ignored here

        Player player = session.getPlayer();
        boolean flying = (message.getFlags() & 0x02) != 0;

        player.setFlying(player.isFlyingAllowed() && flying);
    }
}
