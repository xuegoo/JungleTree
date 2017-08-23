package org.jungletree.rainforest.connector;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface Codec<T extends Message> {

    T decode(ByteBuf buf) throws IOException;

    ByteBuf encode(ByteBuf buf, T message) throws IOException;
}
