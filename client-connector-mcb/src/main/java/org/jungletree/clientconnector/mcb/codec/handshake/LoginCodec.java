package org.jungletree.clientconnector.mcb.codec.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.gomint.jraknet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.jungletree.clientconnector.mcb.packet.handshake.ConnectionInfo;
import org.jungletree.clientconnector.mcb.packet.handshake.ConnectionInfo.Chains;
import org.jungletree.clientconnector.mcb.packet.handshake.LoginPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("Duplicates")
public class LoginCodec implements Codec<LoginPacket> {

    private static final Logger log = LoggerFactory.getLogger(LoginCodec.class);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void encode(Packet msg, PacketBuffer buf) {
        LoginPacket message = (LoginPacket) msg;

        // TODO: Encoding
        buf.writeInt(message.getClientNetworkVersion());
    }

    @Override
    public LoginPacket decode(PacketBuffer buf) {
        LoginPacket message = new LoginPacket();
        message.setClientNetworkVersion(buf.readInt());

        ByteBuf body = asByteBuf(getBody(buf));

        ConnectionInfo info = new ConnectionInfo();

        byte[] chainBytes = readSection(body);
        info.setTokenChain(GSON.fromJson(new String(chainBytes, StandardCharsets.UTF_8), Chains.class));

        byte[] clientDataTokenBytes = readSection(body);
        info.setClientDataToken(new String(clientDataTokenBytes, StandardCharsets.UTF_8));

        message.setConnectionInfo(info);

        return message;
    }

    private ByteBuf asByteBuf(byte[] body) {
        ByteBuf result = ByteBufAllocator.DEFAULT.buffer(body.length);
        result.writeBytes(body);
        return result;
    }

    private byte[] getBody(PacketBuffer buf) {
        int length = buf.readUnsignedVarInt();
        byte[] body = new byte[length];
        buf.readBytes(body);
        return body;
    }

    private byte[] readSection(ByteBuf buf) {
        int length = buf.readIntLE();
        byte[] section = new byte[length];
        buf.readBytes(section);
        return section;
    }
}
