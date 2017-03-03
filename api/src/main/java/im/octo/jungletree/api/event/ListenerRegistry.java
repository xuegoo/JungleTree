package im.octo.jungletree.api.event;

public interface ListenerRegistry {

    <E extends Event> void register(Listener<E> listener, Class<E> eventClass);

    <E extends Event> boolean isRegistered(Listener<E> listener, Class<E> eventClass);
}
