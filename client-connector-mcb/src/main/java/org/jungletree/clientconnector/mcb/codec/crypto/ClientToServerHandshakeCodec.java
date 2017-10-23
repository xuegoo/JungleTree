package org.jungletree.clientconnector.mcb.codec.crypto;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.message.Message;
import org.jungletree.clientconnector.mcb.message.crypto.ClientToServerHandshakeMessage;
import org.jungletree.clientconnector.mcb.message.crypto.ServerToClientHandshakeMessage;

public class ClientToServerHandshakeCodec implements Codec<ClientToServerHandshakeMessage> {

    @Override
    public void encode(Message msg, PacketBuffer buf) {
    }

    @Override
    public ClientToServerHandshakeMessage decode(PacketBuffer buf) {
        return new ClientToServerHandshakeMessage();
    }
}
