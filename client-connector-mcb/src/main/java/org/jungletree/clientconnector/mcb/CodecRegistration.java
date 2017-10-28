package org.jungletree.clientconnector.mcb;

import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.handler.PacketHandler;
import org.jungletree.clientconnector.mcb.packet.Packet;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CodecRegistration {

    private final Map<Integer, Class<? extends Packet>> ids = new ConcurrentHashMap<>();
    private final Map<Class<? extends Packet>, Class<? extends Codec<? extends Packet>>> codecs = new ConcurrentHashMap<>();
    private final Map<Class<? extends Packet>, PacketHandler<? extends Packet>> handlers = new ConcurrentHashMap<>();

    public CodecRegistration() {
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> Class<T> getMessageClass(int id) {
        return (Class<T>) ids.get(id);
    }

    public <T extends Packet> void codec(int id, Class<T> clazz, Class<? extends Codec<T>> codec) {
        this.ids.put(id, clazz);
        this.codecs.put(clazz, codec);
    }

    public <T extends Packet> void handler(Class<T> clazz, PacketHandler<T> handler) {
        this.handlers.put(clazz, handler);
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> Codec<T> getCodec(Class<T> clazz) {
        Class<? extends Codec<T>> codecClazz = (Class<? extends Codec<T>>) codecs.get(clazz);
        try {
            return codecClazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> PacketHandler<T> getHandler(Class<T> clazz) {
        return (PacketHandler<T>) handlers.get(clazz);
    }
}
