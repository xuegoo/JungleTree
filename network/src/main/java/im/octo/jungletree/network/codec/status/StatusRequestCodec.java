package im.octo.jungletree.network.codec.status;

import com.flowpowered.network.Codec;
import io.netty.buffer.ByteBuf;
import im.octo.jungletree.network.message.status.StatusRequestMessage;

import java.io.IOException;

public class StatusRequestCodec implements Codec<StatusRequestMessage> {

    @Override
    public StatusRequestMessage decode(ByteBuf buffer) throws IOException {
        return new StatusRequestMessage();
    }

    @Override
    public ByteBuf encode(ByteBuf buf, StatusRequestMessage message) throws IOException {
        return buf;
    }
}
