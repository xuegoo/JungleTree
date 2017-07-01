package im.octo.jungletree.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Singleton;
import im.octo.jungletree.rainforest.scheduler.Task;
import im.octo.jungletree.rainforest.scheduler.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Singleton
public final class JScheduler implements Scheduler {

    private static final Logger log = LoggerFactory.getLogger(JScheduler.class);

    private final ScheduledExecutorService executor;

    public JScheduler() {
        executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactoryBuilder()
                .setNameFormat("scheduler-%d")
                .setUncaughtExceptionHandler(new SchedulerExceptionHandler())
                .build());
        // Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
    }

    @Override
    public void execute(Task task) {
        executor.execute(task);
    }

    @Override
    public void shutdown() {
        this.executor.shutdown();
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }

    class SchedulerExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable ex) {
            log.error("Uncaught exception in thread \"{}\":", t.getName());
            log.error("", ex);
        }
    }
}
