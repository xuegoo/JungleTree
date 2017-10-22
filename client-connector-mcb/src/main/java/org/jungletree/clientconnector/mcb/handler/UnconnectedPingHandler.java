package org.jungletree.clientconnector.mcb.handler;

import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import org.jungletree.clientconnector.mcb.BedrockServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class UnconnectedPingHandler implements SocketEventHandler {

    private static final Logger log = LoggerFactory.getLogger(UnconnectedPingHandler.class);

    private final BedrockServer server;

    public UnconnectedPingHandler(BedrockServer server) {
        this.server = server;
    }

    @Override
    public void onSocketEvent(Socket socket, SocketEvent socketEvent) {
        socketEvent.getPingPongInfo().setMotd(String.format(
                "MCPE;%s;%d;%s;%d;%d;%s",
                server.getMotd(),
                server.getProtocolVersion(),
                server.getProtocolVersionName(),
                server.getOnlinePlayers(),
                server.getMaxPlayers(),
                Long.toUnsignedString(server.getServerUuid().getLeastSignificantBits())
        ));
    }
}
