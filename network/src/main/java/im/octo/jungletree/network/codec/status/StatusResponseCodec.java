package im.octo.jungletree.network.codec.status;

import com.flowpowered.network.Codec;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import im.octo.jungletree.network.message.status.StatusResponseMessage;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.readUTF8;
import static com.flowpowered.network.util.ByteBufUtils.writeUTF8;

public class StatusResponseCodec implements Codec<StatusResponseMessage> {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public StatusResponseMessage decode(ByteBuf buffer) throws IOException {
        String json = readUTF8(buffer);
        return new StatusResponseMessage(GSON.fromJson(json, ServerListPingResponseObject.class));
    }

    @Override
    public ByteBuf encode(ByteBuf buf, StatusResponseMessage message) throws IOException {
        writeUTF8(buf, GSON.toJson(message.getResponseObject()));
        return buf;
    }
}
