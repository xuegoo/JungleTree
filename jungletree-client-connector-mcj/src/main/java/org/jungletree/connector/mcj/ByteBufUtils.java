package org.jungletree.connector.mcj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;
import org.jungletree.rainforest.util.Text;

import java.io.IOException;

import static com.flowpowered.network.util.ByteBufUtils.readUTF8;
import static com.flowpowered.network.util.ByteBufUtils.writeUTF8;

public final class ByteBufUtils {

    private static final Gson GSON = new GsonBuilder().create();

    public static void writeText(ByteBuf buf, Text text) throws IOException {
        writeUTF8(buf, GSON.toJson(text));
    }

    public static void writeBlockPosition(ByteBuf buf, long x, long y, long z) {
        buf.writeLong((x & 0x3ffffff) << 38 | (y & 0xfff) << 26 | z & 0x3ffffff);
    }

    public static Text readText(ByteBuf buf) throws IOException {
        return GSON.fromJson(readUTF8(buf), Text.class);
    }
}
