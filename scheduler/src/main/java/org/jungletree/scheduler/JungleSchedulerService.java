package org.jungletree.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.jungletree.rainforest.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JungleSchedulerService implements SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(JungleSchedulerService.class);

    private final ExecutorService executorService;

    public JungleSchedulerService() {
        this.executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactoryBuilder()
                .setNameFormat("scheduler-%d")
                .setUncaughtExceptionHandler(new SchedulerExceptionHandler())
                .build());
        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));
    }

    @Override
    public void execute(Runnable task) {
        executorService.execute(task);
    }

    @Override
    public void shutdownGracefully() {
        executorService.shutdown();
    }

    @Override
    public void forceShutdown() {
        executorService.shutdownNow();
    }

    class SchedulerExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable ex) {
            log.error("Uncaught exception in thread \"{}\":", t.getName());
            log.error("", ex);
        }
    }
}
