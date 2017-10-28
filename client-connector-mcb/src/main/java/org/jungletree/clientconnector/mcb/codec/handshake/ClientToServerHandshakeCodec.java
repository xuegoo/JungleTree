package org.jungletree.clientconnector.mcb.codec.handshake;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.jungletree.clientconnector.mcb.packet.handshake.ClientToServerHandshakePacket;

public class ClientToServerHandshakeCodec implements Codec<ClientToServerHandshakePacket> {

    @Override
    public void encode(Packet msg, PacketBuffer buf) {
    }

    @Override
    public ClientToServerHandshakePacket decode(PacketBuffer buf) {
        return new ClientToServerHandshakePacket();
    }
}
