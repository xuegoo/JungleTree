package org.jungletree.clientconnector.mcb.codec;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.packet.DisconnectPacket;
import org.jungletree.clientconnector.mcb.packet.Packet;

public class DisconnectPacketCodec implements Codec<DisconnectPacket> {

    @Override
    public void encode(Packet packet, PacketBuffer buf) {
        buf.writeString(((DisconnectPacket) packet).getReason());
    }

    @Override
    public DisconnectPacket decode(PacketBuffer buf) {
        DisconnectPacket packet = new DisconnectPacket();
        packet.setReason(buf.readString());
        return packet;
    }
}
