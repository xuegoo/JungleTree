package im.octo.jungletree.network.handler.handshake;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.handshake.HandshakeMessage;

public class HandshakeHandler implements MessageHandler<JSession, HandshakeMessage> {

    @Override
    public void handle(JSession session, HandshakeMessage message) {
    }
}
