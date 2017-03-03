package im.octo.jungletree.api.event;

import java.util.Set;

public interface ListenerRegistry {

    <E extends Event> Set<Listener<? extends Event>> getListeners(Class<E> eventClass);

    <E extends Event> void register(Listener<E> listener, Class<E> eventClass);

    <E extends Event> void unregister(Listener<E> listener, Class<E> eventClass);

    <E extends Event> boolean isRegistered(Listener<E> listener, Class<E> eventClass);
}
