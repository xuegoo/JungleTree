package org.jungletree.clientconnector.mcb.codec;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.packet.DisconnectPacket;
import org.jungletree.clientconnector.mcb.packet.Packet;

public class DisconnectPacketCodec implements Codec<DisconnectPacket> {

    @Override
    public void encode(Packet msg, PacketBuffer buf) {
        DisconnectPacket packet = (DisconnectPacket) msg;
        buf.writeBoolean(packet.isHideScreen());
        if (!packet.isHideScreen()) {
            buf.writeString(packet.getReason());
        }
    }

    @Override
    public DisconnectPacket decode(PacketBuffer buf) {
        DisconnectPacket packet = new DisconnectPacket();
        packet.setHideScreen(buf.readBoolean());
        if (!packet.isHideScreen()) {
            packet.setReason(buf.readString());
        }
        return packet;
    }
}
