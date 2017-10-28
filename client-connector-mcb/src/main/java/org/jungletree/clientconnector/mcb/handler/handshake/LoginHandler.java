package org.jungletree.clientconnector.mcb.handler.handshake;

import org.jungletree.clientconnector.mcb.BedrockServer;
import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.auth.AuthValidator;
import org.jungletree.clientconnector.mcb.exception.NotAuthorizedException;
import org.jungletree.clientconnector.mcb.handler.PacketHandler;
import org.jungletree.clientconnector.mcb.packet.handshake.LoginPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHandler implements PacketHandler<LoginPacket> {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    private final BedrockServer server;
    private final AuthValidator validator;

    public LoginHandler(BedrockServer server) {
        this.server = server;
        this.validator = new AuthValidator();
    }

    @Override
    public void handle(ClientConnection client, LoginPacket message) {
        log.info("Got login request: {}", message.toString());

        try {
            validator.validate(client, message.getConnectionInfo());
        } catch (NotAuthorizedException ex) {
            log.warn("Client unauthorized", ex);
            server.disconnectClient(client, ex.getMessage());
        }
        log.info("Client authorized");
    }
}
