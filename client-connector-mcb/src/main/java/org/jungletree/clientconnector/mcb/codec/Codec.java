package org.jungletree.clientconnector.mcb.codec;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.packet.Packet;

public interface Codec<T extends Packet> {

    void encode(Packet packet, PacketBuffer buf);

    T decode(PacketBuffer buf);
}
