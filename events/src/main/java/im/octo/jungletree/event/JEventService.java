package im.octo.jungletree.event;

import com.google.inject.Singleton;
import im.octo.jungletree.api.event.Event;
import im.octo.jungletree.api.event.EventService;

@Singleton
public class JEventService implements EventService {

    @Override
    public void call(Event event) {
    }
}
