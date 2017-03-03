package im.octo.jungletree.api.event;

public interface EventService {

    <E extends Event> void call(E event);
}
