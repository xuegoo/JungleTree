package org.jungletree.clientconnector.mcb.handler.handshake;

import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.PlayState;
import org.jungletree.clientconnector.mcb.handler.PacketHandler;
import org.jungletree.clientconnector.mcb.packet.handshake.ClientToServerHandshakePacket;
import org.jungletree.clientconnector.mcb.packet.handshake.PlayStatePacket;
import org.jungletree.clientconnector.mcb.packet.resourcepack.ResourcePackInfoPacket;
import org.jungletree.clientconnector.mcb.packet.resourcepack.ResourcePackStackPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class ClientToServerHandshakeHandler implements PacketHandler<ClientToServerHandshakePacket> {

    private static final Logger log = LoggerFactory.getLogger(ClientToServerHandshakeHandler.class);

    @Override
    public void handle(ClientConnection client, ClientToServerHandshakePacket message) {
        log.info("Client responded by enabling encryption!: {}", message.toString());

        sendPlayState(client);
        sendResourcePacks(client);
    }

    private void sendPlayState(ClientConnection client) {
        log.info("Changing play state to LOGIN_SUCCESS");

        PlayStatePacket packet = new PlayStatePacket();
        packet.setPlayState(PlayState.LOGIN_SUCCESS);
        client.send(packet);
    }

    private void sendResourcePacks(ClientConnection client) {
        ResourcePackInfoPacket infoPacket = new ResourcePackInfoPacket();
        infoPacket.setMustAccept(false);
        infoPacket.setBehaviorPackInfo(Collections.emptyList());
        infoPacket.setResourcePackInfo(Collections.emptyList());

        ResourcePackStackPacket stackPacket = new ResourcePackStackPacket();
        stackPacket.setMustAccept(false);
        stackPacket.setBehaviorPackIdVersions(Collections.emptyList());
        stackPacket.setResourcePackIdVersions(Collections.emptyList());

        client.send(infoPacket);
        client.send(stackPacket);
    }
}
