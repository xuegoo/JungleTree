package im.octo.jungletree;

import com.google.inject.AbstractModule;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.ListenerRegistry;
import im.octo.jungletree.api.scheduler.TaskScheduler;
import im.octo.jungletree.event.JEventService;
import im.octo.jungletree.event.JListenerRegistry;
import im.octo.jungletree.scheduler.JTaskScheduler;

public class JungleGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TaskScheduler.class).to(JTaskScheduler.class);
        bind(EventService.class).to(JEventService.class);
        bind(ListenerRegistry.class).to(JListenerRegistry.class);
    }
}
