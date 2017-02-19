package im.octo.jungletree.network.codec.handshake;

import com.flowpowered.network.Codec;
import io.netty.buffer.ByteBuf;
import im.octo.jungletree.network.message.handshake.HandshakeMessage;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.*;

public class HandshakeCodec implements Codec<HandshakeMessage> {

    @Override
    public HandshakeMessage decode(ByteBuf buffer) throws IOException {
        int protocol = readVarInt(buffer);
        String address = readUTF8(buffer);
        int port = buffer.readUnsignedShort();
        int state = readVarInt(buffer);
        return new HandshakeMessage(protocol, address, port, state);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, HandshakeMessage message) throws IOException {
        writeVarInt(buf, message.getProtocolVersion());
        writeUTF8(buf, message.getAddress());
        buf.writeShort(message.getPort());
        writeVarInt(buf, message.getState());
        return buf;
    }
}
