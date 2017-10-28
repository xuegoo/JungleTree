module jungletree.scheduler {
    requires rainforest.scheduler;
    requires guava;
    requires org.slf4j;
    exports org.jungletree.scheduler;

    provides org.jungletree.rainforest.scheduler.Scheduler with org.jungletree.scheduler.JungleSchedulerService;
}
