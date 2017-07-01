package im.octo.jungletree.network;

import com.google.inject.AbstractModule;
import im.octo.jungletree.rainforest.scheduler.Scheduler;

public class NetworkGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Scheduler.class).to(JScheduler.class);
    }
}
