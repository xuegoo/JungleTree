package im.octo.jungletree.event;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.event.Event;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.Listener;
import im.octo.jungletree.api.event.ListenerRegistry;
import im.octo.jungletree.api.scheduler.TaskScheduler;

import java.util.Set;

@Singleton
public class JEventService implements EventService {

    private final ListenerRegistry listenerRegistry;
    private final TaskScheduler scheduler;

    public JEventService() {
        Server server = Rainforest.getServer();
        Injector guice = server.getGuice();
        this.scheduler = guice.getInstance(TaskScheduler.class);
        this.listenerRegistry = guice.getInstance(ListenerRegistry.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends Event> void call(E event) {
        Set<Listener<? extends Event>> listeners = listenerRegistry.getListeners(event.getClass());
        listeners.forEach(listener -> {
            if (listener.getListenerType().isInstance(event)) {
                scheduler.execute(() -> ((Listener<E>)listener).handle(event));
            }
        });
    }
}
