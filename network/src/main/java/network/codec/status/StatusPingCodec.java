package network.codec.status;

import com.flowpowered.network.Codec;
import io.netty.buffer.ByteBuf;
import network.message.status.StatusPingMessage;

import java.io.IOException;

public class StatusPingCodec implements Codec<StatusPingMessage> {

    @Override
    public StatusPingMessage decode(ByteBuf buffer) throws IOException {
        return new StatusPingMessage(buffer.readLong());
    }

    @Override
    public ByteBuf encode(ByteBuf buf, StatusPingMessage message) throws IOException {
        buf.writeLong(message.getTime());
        return buf;
    }
}
