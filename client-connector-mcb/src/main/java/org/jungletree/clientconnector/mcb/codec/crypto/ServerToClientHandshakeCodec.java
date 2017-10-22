package org.jungletree.clientconnector.mcb.codec.crypto;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.message.Message;
import org.jungletree.clientconnector.mcb.message.crypto.ServerToClientHandshakeMessage;

public class ServerToClientHandshakeCodec implements Codec<ServerToClientHandshakeMessage> {

    @Override
    public void encode(Message msg, PacketBuffer buf) {
        ServerToClientHandshakeMessage message = (ServerToClientHandshakeMessage) msg;
        buf.writeString(message.getServerToken());
    }

    @Override
    public ServerToClientHandshakeMessage decode(PacketBuffer buf) {
        ServerToClientHandshakeMessage message = new ServerToClientHandshakeMessage();
        message.setServerToken(buf.readString());
        return message;
    }
}
