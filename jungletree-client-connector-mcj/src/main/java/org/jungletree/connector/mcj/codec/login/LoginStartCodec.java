package org.jungletree.connector.mcj.codec.login;

import com.flowpowered.network.Codec;
import io.netty.buffer.ByteBuf;
import org.jungletree.connector.mcj.message.login.LoginStartMessage;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.readUTF8;
import static com.flowpowered.network.util.ByteBufUtils.writeUTF8;

public class LoginStartCodec implements Codec<LoginStartMessage> {

    @Override
    public LoginStartMessage decode(ByteBuf buffer) throws IOException {
        return new LoginStartMessage(readUTF8(buffer));
    }

    @Override
    public ByteBuf encode(ByteBuf buf, LoginStartMessage message) throws IOException {
        writeUTF8(buf, message.getUsername());
        return buf;
    }
}
