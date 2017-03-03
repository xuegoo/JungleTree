package im.octo.jungletree.event;

import com.google.inject.Singleton;
import im.octo.jungletree.api.event.Event;
import im.octo.jungletree.api.event.Listener;
import im.octo.jungletree.api.event.ListenerRegistry;
import im.octo.jungletree.api.exception.ListenerAlreadyRegisteredException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class JListenerRegistry implements ListenerRegistry {

    private final Map<Class<? extends Event>, Set<Listener<? extends Event>>> registry = new ConcurrentHashMap<>();

    @Override
    public <E extends Event> void register(Listener<E> listener, Class<E> eventClass) {
        if (isRegistered(listener, eventClass)) {
            throw new ListenerAlreadyRegisteredException(String.format(
                    "Listener \"%s\" is already registered for the event type \"%s\"",
                    listener.getClass().getSimpleName(),
                    eventClass.getSimpleName())
            );
        }
        addListener(listener, eventClass);
    }

    public <E extends Event> void unregister(Listener<E> listener, Class<E> eventClass) {
        if (!isRegistered(listener, eventClass)) {
            return;
        }
        removeListener(listener, eventClass);
    }

    @Override
    public <E extends Event> boolean isRegistered(Listener<E> listener, Class<E> eventClass) {
        if (!registry.containsKey(eventClass)) {
            return false;
        }

        Set<Listener<? extends Event>> listeners = registry.get(eventClass);
        return listeners.stream().anyMatch(l ->
                l.getListenerType().equals(listener.getListenerType())
        );
    }

    private <E extends Event> void addListener(Listener<E> listener, Class<E> eventClass) {
        Set<Listener<? extends Event>> listeners = registry.getOrDefault(eventClass, Collections.synchronizedSet(new HashSet<>()));
        listeners.add(listener);
        registry.put(eventClass, listeners);
    }

    private <E extends Event> void removeListener(Listener<E> listener, Class<E> eventClass) {
        Set<Listener<? extends Event>> listeners = registry.getOrDefault(eventClass, Collections.synchronizedSet(new HashSet<>()));
        listeners.remove(listener);
        registry.put(eventClass, listeners);
    }
}
