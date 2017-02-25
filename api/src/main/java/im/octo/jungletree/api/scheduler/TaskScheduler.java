package im.octo.jungletree.api.scheduler;

public interface TaskScheduler {

    void execute(Task task);

    void shutdown();
}
