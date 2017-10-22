package org.jungletree.clientconnector.mcb.codec;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.message.Message;

public interface Codec<T extends Message> {

    void encode(Message message, PacketBuffer buf);

    T decode(PacketBuffer buf);
}
