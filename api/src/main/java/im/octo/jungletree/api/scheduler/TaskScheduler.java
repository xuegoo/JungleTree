package im.octo.jungletree.api.scheduler;

public interface TaskScheduler {

    void execute(DaemonTask task);

    void shutdown();
}
