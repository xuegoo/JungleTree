package im.octo.jungletree.network.codec.login;

import com.flowpowered.network.Codec;
import io.netty.buffer.ByteBuf;
import im.octo.jungletree.network.message.login.EncryptionKeyResponseMessage;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.readVarInt;
import static com.flowpowered.network.util.ByteBufUtils.writeVarInt;

public class EncryptionKeyResponseCodec implements Codec<EncryptionKeyResponseMessage> {

    @Override
    public EncryptionKeyResponseMessage decode(ByteBuf buffer) throws IOException {
        byte[] sharedSecret = new byte[readVarInt(buffer)];
        buffer.readBytes(sharedSecret);

        byte[] verifyToken = new byte[readVarInt(buffer)];
        buffer.readBytes(verifyToken);

        return new EncryptionKeyResponseMessage(sharedSecret, verifyToken);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, EncryptionKeyResponseMessage message) throws IOException {
        writeVarInt(buf, message.getSharedSecret().length);
        buf.writeBytes(message.getSharedSecret());

        writeVarInt(buf, message.getVerifyToken().length);
        buf.writeBytes(message.getVerifyToken());
        return buf;
    }
}
