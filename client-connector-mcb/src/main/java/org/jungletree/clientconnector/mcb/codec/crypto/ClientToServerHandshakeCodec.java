package org.jungletree.clientconnector.mcb.codec.crypto;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.jungletree.clientconnector.mcb.packet.crypto.ClientToServerHandshakePacket;

public class ClientToServerHandshakeCodec implements Codec<ClientToServerHandshakePacket> {

    @Override
    public void encode(Packet msg, PacketBuffer buf) {
    }

    @Override
    public ClientToServerHandshakePacket decode(PacketBuffer buf) {
        return new ClientToServerHandshakePacket();
    }
}
