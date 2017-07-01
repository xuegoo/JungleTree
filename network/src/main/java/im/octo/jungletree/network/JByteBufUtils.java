package im.octo.jungletree.network;

import im.octo.jungletree.api.util.Vector;
import io.netty.buffer.ByteBuf;

public final class JByteBufUtils {

    public static Vector readBlockPosition(ByteBuf buffer) {
        // http://wiki.vg/Protocol#Position
        long val = buffer.readLong();
        long x = val >> 38;
        long y = val >> 26 & 0xFFF;
        long z = val << 38 >> 38;
        return new Vector(x, y, z);
    }

    public static void writeBlockPosition(ByteBuf buf, Vector vector) {
        writeBlockPosition(buf, vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public static void writeBlockPosition(ByteBuf buf, long x, long y, long z) {
        buf.writeLong((x & 0x3ffffff) << 38 | (y & 0xfff) << 26 | z & 0x3ffffff);
    }
}
