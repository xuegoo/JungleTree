package org.jungletree.rainforest.connector;

public interface CodecRegistration {

    int getOpCode();

    <T extends Message> Codec<T> getCodec();
}
