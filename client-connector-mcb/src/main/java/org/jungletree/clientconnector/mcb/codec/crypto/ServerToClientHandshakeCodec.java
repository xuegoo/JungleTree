package org.jungletree.clientconnector.mcb.codec.crypto;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.jungletree.clientconnector.mcb.packet.crypto.ServerToClientHandshakePacket;

public class ServerToClientHandshakeCodec implements Codec<ServerToClientHandshakePacket> {

    @Override
    public void encode(Packet msg, PacketBuffer buf) {
        ServerToClientHandshakePacket message = (ServerToClientHandshakePacket) msg;
        buf.writeString(message.getServerToken());
    }

    @Override
    public ServerToClientHandshakePacket decode(PacketBuffer buf) {
        ServerToClientHandshakePacket message = new ServerToClientHandshakePacket();
        message.setServerToken(buf.readString());
        return message;
    }
}
