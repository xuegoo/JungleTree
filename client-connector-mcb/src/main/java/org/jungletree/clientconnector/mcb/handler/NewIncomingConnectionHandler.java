package org.jungletree.clientconnector.mcb.handler;

import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import org.jungletree.clientconnector.mcb.BedrockServer;
import org.jungletree.clientconnector.mcb.ClientConnection;

public class NewIncomingConnectionHandler implements SocketEventHandler {

    private final BedrockServer server;

    public NewIncomingConnectionHandler(BedrockServer server) {
        this.server = server;
    }

    @Override
    public void onSocketEvent(Socket socket, SocketEvent socketEvent) {
        server.connectClient(socket, new ClientConnection(server, socketEvent.getConnection()));
    }
}
