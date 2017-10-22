package org.jungletree.clientconnector.mcb.handler;

import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import org.jungletree.clientconnector.mcb.BedrockServer;

public class GeneralDisconnectHandler implements SocketEventHandler {

    private final BedrockServer server;

    public GeneralDisconnectHandler(BedrockServer server) {
        this.server = server;
    }

    @Override
    public void onSocketEvent(Socket socket, SocketEvent event) {
        server.disconnectClient(socket);
    }
}
