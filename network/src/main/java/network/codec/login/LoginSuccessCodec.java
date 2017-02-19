package network.codec.login;

import com.flowpowered.network.Codec;
import io.netty.buffer.ByteBuf;
import network.message.login.LoginSuccessMessage;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.readUTF8;
import static com.flowpowered.network.util.ByteBufUtils.writeUTF8;

public class LoginSuccessCodec implements Codec<LoginSuccessMessage> {

    @Override
    public LoginSuccessMessage decode(ByteBuf buffer) throws IOException {
        String uuid = readUTF8(buffer);
        String username = readUTF8(buffer);
        return new LoginSuccessMessage(uuid, username);
    }

    @Override
    public ByteBuf encode(ByteBuf buf, LoginSuccessMessage message) throws IOException {
        writeUTF8(buf, message.getUuid());
        writeUTF8(buf, message.getUsername());
        return buf;
    }
}
