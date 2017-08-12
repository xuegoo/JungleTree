package org.jungletree.rainforest.scheduler;

public interface SchedulerService {

    void execute(Runnable task);

    void shutdownGracefully();

    void forceShutdown();
}
