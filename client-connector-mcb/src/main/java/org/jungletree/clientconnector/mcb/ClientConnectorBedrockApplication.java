package org.jungletree.clientconnector.mcb;

import io.gomint.jraknet.SocketEvent;
import org.jungletree.clientconnector.mcb.handler.GeneralDisconnectHandler;
import org.jungletree.clientconnector.mcb.handler.NewIncomingConnectionHandler;
import org.jungletree.clientconnector.mcb.handler.UnconnectedPingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientConnectorBedrockApplication {

    private static final Logger log = LoggerFactory.getLogger(ClientConnectorBedrockApplication.class);

    private final BedrockServer bedrockServer;

    private ClientConnectorBedrockApplication() {
        this.bedrockServer = new BedrockServer();
        init();
    }

    private void init() {
        log.info("Setting parameters");
        bedrockServer.setHost("0.0.0.0");
        bedrockServer.setPort(19132);
        bedrockServer.setMotd("§2JungleTree Demo§r");
        bedrockServer.setProtocolVersion(137);
        bedrockServer.setProtocolVersionName("1.2.5.12");
        bedrockServer.setOnlinePlayers(1336);
        bedrockServer.setMaxPlayers(1337);

        bedrockServer.registerSocketEventHandler(SocketEvent.Type.UNCONNECTED_PING, new UnconnectedPingHandler(bedrockServer));
        bedrockServer.registerSocketEventHandler(SocketEvent.Type.NEW_INCOMING_CONNECTION, new NewIncomingConnectionHandler(bedrockServer));

        GeneralDisconnectHandler disconnectHandler = new GeneralDisconnectHandler(bedrockServer);
        bedrockServer.registerSocketEventHandler(SocketEvent.Type.CONNECTION_CLOSED, disconnectHandler);
        bedrockServer.registerSocketEventHandler(SocketEvent.Type.CONNECTION_ATTEMPT_FAILED, disconnectHandler);
        bedrockServer.registerSocketEventHandler(SocketEvent.Type.CONNECTION_DISCONNECTED, disconnectHandler);
        bedrockServer.start();
    }

    public static void main(String[] args) {
        new ClientConnectorBedrockApplication();
    }
}
