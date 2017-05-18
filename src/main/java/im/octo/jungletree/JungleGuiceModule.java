package im.octo.jungletree;

import com.google.inject.AbstractModule;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.ListenerRegistry;
import im.octo.jungletree.api.player.PlayerDataService;
import im.octo.jungletree.api.scheduler.TaskScheduler;
import im.octo.jungletree.api.world.WorldService;
import im.octo.jungletree.api.world.generator.NormalWorldGenerator;
import im.octo.jungletree.api.world.generator.WorldGeneratorService;
import im.octo.jungletree.event.JEventService;
import im.octo.jungletree.event.JListenerRegistry;
import im.octo.jungletree.scheduler.JTaskScheduler;
import im.octo.jungletree.player.JPlayerDataService;
import im.octo.jungletree.world.JWorldService;
import im.octo.jungletree.world.generator.JNormalWorldGenerator;
import im.octo.jungletree.world.generator.JWorldGeneratorService;

public class JungleGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        // Core Utilities
        bind(TaskScheduler.class).to(JTaskScheduler.class);
        bind(EventService.class).to(JEventService.class);
        bind(ListenerRegistry.class).to(JListenerRegistry.class);
        bind(PlayerDataService.class).to(JPlayerDataService.class);

        // World
        bind(WorldService.class).to(JWorldService.class);

        // World Generation
        bind(WorldGeneratorService.class).to(JWorldGeneratorService.class);
        bind(NormalWorldGenerator.class).to(JNormalWorldGenerator.class);
    }
}
