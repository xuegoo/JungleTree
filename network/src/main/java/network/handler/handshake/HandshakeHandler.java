package network.handler.handshake;

import com.flowpowered.network.MessageHandler;
import network.JSession;
import network.message.handshake.HandshakeMessage;

public class HandshakeHandler implements MessageHandler<JSession, HandshakeMessage> {

    @Override
    public void handle(JSession session, HandshakeMessage message) {
    }
}
