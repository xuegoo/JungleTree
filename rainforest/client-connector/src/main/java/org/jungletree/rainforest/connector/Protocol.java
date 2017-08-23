package org.jungletree.rainforest.connector;

import io.netty.buffer.ByteBuf;
import org.jungletree.rainforest.connector.exception.UnknownPacketException;

public interface Protocol {

    String getName();

    Codec<?> readHeader(ByteBuf buf) throws UnknownPacketException;

    <T extends Message> CodecRegistration getCodecRegistration(Class<T> messageClass);

    ByteBuf writeHeader(ByteBuf header, CodecRegistration registration, ByteBuf data);
}
