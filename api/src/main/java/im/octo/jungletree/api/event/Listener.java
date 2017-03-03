package im.octo.jungletree.api.event;

public interface Listener<E extends Event> {

    Class<E> getListenerType();

    Event handle(E event);
}
