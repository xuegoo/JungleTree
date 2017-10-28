package org.jungletree.clientconnector.mcb.handler;

import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.packet.Packet;

public interface PacketHandler<T extends Packet> {

    void handle(ClientConnection client, T message);
}
