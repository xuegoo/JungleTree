package im.octo.jungletree.network.codec.login;

import com.flowpowered.network.Codec;
import im.octo.jungletree.network.message.login.EncryptionKeyRequestMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.*;

public class EncryptionKeyRequestCodec implements Codec<EncryptionKeyRequestMessage> {

    @Override
    public EncryptionKeyRequestMessage decode(ByteBuf buffer) throws IOException {
        String serverId = readUTF8(buffer);

        byte[] publicKey = new byte[readVarInt(buffer)];
        buffer.readBytes(publicKey);

        byte[] verifyToken = new byte[readVarInt(buffer)];
        buffer.readBytes(verifyToken);

        return new EncryptionKeyRequestMessage(serverId, publicKey, verifyToken);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, EncryptionKeyRequestMessage message) throws IOException {
        writeUTF8(buf, message.getServerId());
        writeVarInt(buf, message.getPublicKey().length);
        buf.writeBytes(message.getPublicKey());
        writeVarInt(buf, message.getVerifyToken().length);
        buf.writeBytes(message.getVerifyToken());
        return buf;
    }
}
