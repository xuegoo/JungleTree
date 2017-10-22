package org.jungletree.clientconnector.mcb;

import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.handler.MessageHandler;
import org.jungletree.clientconnector.mcb.message.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodecRegistration {

    private final Map<Integer, Class<? extends Message>> ids = new ConcurrentHashMap<>();
    private final Map<Class<? extends Message>, Class<? extends Codec<? extends Message>>> codecs = new ConcurrentHashMap<>();
    private final Map<Class<? extends Message>, MessageHandler<? extends Message>> handlers = new ConcurrentHashMap<>();

    public CodecRegistration() {
    }

    @SuppressWarnings("unchecked")
    public <T extends Message> Class<T> getMessageClass(int id) {
        return (Class<T>) ids.get(id);
    }

    public <T extends Message> void codec(int id, Class<T> clazz, Class<? extends Codec<T>> codec) {
        this.ids.put(id, clazz);
        this.codecs.put(clazz, codec);
    }

    public <T extends Message> void handler(Class<T> clazz, MessageHandler<T> handler) {
        this.handlers.put(clazz, handler);
    }

    @SuppressWarnings("unchecked")
    public <T extends Message> Codec<T> getCodec(Class<T> clazz) {
        Class<? extends Codec<T>> codecClazz = (Class<? extends Codec<T>>) codecs.get(clazz);
        try {
            return codecClazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Message> MessageHandler<T> getHandler(Class<T> clazz) {
        return (MessageHandler<T>) handlers.get(clazz);
    }
}
