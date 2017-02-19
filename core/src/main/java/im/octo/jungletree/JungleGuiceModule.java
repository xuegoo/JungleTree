package im.octo.jungletree;

import com.google.inject.AbstractModule;
import im.octo.jungletree.api.scheduler.TaskScheduler;
import im.octo.jungletree.scheduler.JTaskScheduler;

public class JungleGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TaskScheduler.class).to(JTaskScheduler.class);
    }
}
