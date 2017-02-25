package im.octo.jungletree.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Singleton;
import im.octo.jungletree.api.scheduler.Task;
import im.octo.jungletree.api.scheduler.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Singleton
public class JTaskScheduler implements TaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(JTaskScheduler.class);

    private final ThreadPoolExecutor executor;

    public JTaskScheduler() {
        this.executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                new ThreadFactoryBuilder()
                        .setNameFormat("scheduler-%d")
                        .setUncaughtExceptionHandler(new SchedulerExceptionHandler())
                        .build()
        );
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
    }

    @Override
    public void execute(Task task) {
        executor.execute(task);
    }

    @Override
    public void shutdown() {
        this.executor.shutdown();
    }

    class SchedulerExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable ex) {
            log.error("Uncaught exception in thread \"{}\":", t.getName());
            log.error("", ex);
        }
    }
}
