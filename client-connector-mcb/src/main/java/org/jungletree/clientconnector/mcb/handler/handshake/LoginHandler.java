package org.jungletree.clientconnector.mcb.handler.handshake;

import org.jungletree.clientconnector.mcb.BedrockServer;
import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.auth.AuthValidator;
import org.jungletree.clientconnector.mcb.crypto.ServerToken;
import org.jungletree.clientconnector.mcb.crypto.ClientSaltTokenFactory;
import org.jungletree.clientconnector.mcb.exception.NotAuthorizedException;
import org.jungletree.clientconnector.mcb.handler.PacketHandler;
import org.jungletree.clientconnector.mcb.packet.handshake.ServerToClientHandshakePacket;
import org.jungletree.clientconnector.mcb.packet.handshake.LoginPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.interfaces.ECPublicKey;

public class LoginHandler implements PacketHandler<LoginPacket> {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    private final BedrockServer server;
    private final ClientSaltTokenFactory tokenFactory;
    private final AuthValidator validator;

    public LoginHandler(BedrockServer server) {
        this.server = server;
        this.tokenFactory = server.getClientSaltTokenFactory();
        this.validator = new AuthValidator();
    }

    @Override
    public void handle(ClientConnection client, LoginPacket message) {
        log.info("Got login request: {}", message.toString());

        ECPublicKey key;
        try {
            key = validator.validate(message.getConnectionInfo());
        } catch (NotAuthorizedException ex) {
            log.warn("Client unauthorized", ex);
            server.disconnectClient(client, ex.getMessage());
            return;
        }
        log.info("Client authorized");
        client.flush();

        client.getProtocolEncryption().setClientPublicKey(key);

        ServerToken token = tokenFactory.generateClientSaltToken();
        client.getProtocolEncryption().setClientSalt(token.getClientSalt());

        ServerToClientHandshakePacket handshake = new ServerToClientHandshakePacket();
        handshake.setServerToken(token.getServerToken());
        client.send(handshake);

        boolean success = client.getProtocolEncryption().beginClientsideEncryption();
        if (success) {
            client.setEncryptionEnabled(true);
            log.info("Encryption enabled");
        } else {
            log.info("Encryption failed!");
        }
    }
}
