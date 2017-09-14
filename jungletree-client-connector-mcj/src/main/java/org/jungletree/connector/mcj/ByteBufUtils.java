package org.jungletree.connector.mcj;

import io.netty.buffer.ByteBuf;

public final class ByteBufUtils {

    public static void writeBlockPosition(ByteBuf buf, long x, long y, long z) {
        buf.writeLong((x & 0x3ffffff) << 38 | (y & 0xfff) << 26 | z & 0x3ffffff);
    }
}
