module jungletree.scheduler {
    requires rainforest.scheduler;
    requires slf4j.api;
    requires guava;
    exports org.jungletree.scheduler;

    provides org.jungletree.rainforest.scheduler.SchedulerService with org.jungletree.scheduler.JungleSchedulerService;
}
