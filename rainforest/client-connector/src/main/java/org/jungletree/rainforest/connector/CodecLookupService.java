package org.jungletree.rainforest.connector;

import org.jungletree.rainforest.connector.exception.IllegalOpCodeException;

import java.lang.reflect.InvocationTargetException;

public interface CodecLookupService {

    <T extends Message, C extends Codec<? super T>> CodecRegistration bind(Class<T> messageClass, Class<C> codec, int opCode) throws InstantiationException, IllegalAccessException, InvocationTargetException;

    Codec<?> find(int opCode) throws IllegalOpCodeException;

    <T extends Message> CodecRegistration find(Class<T> messageClass);
}
