package im.octo.jungletree.rainforest.scheduler;

public interface Scheduler {

    void execute(Runnable task);

    void shutdownGracefully();

    void forceShutdown();
}