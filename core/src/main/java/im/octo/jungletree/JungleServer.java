package im.octo.jungletree;

import com.google.inject.Guice;
import com.google.inject.Injector;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.scheduler.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JungleServer implements Server {

    private static final Logger log = LoggerFactory.getLogger(JungleServer.class);

    private final Injector guice;
    private final TaskScheduler scheduler;

    private JungleServer() {
        this.guice = Guice.createInjector(new JungleGuiceModule());
        this.scheduler = guice.getInstance(TaskScheduler.class);

        scheduler.execute(this::logStartMessage);
        scheduler.shutdown();
    }

    public Injector getGuice() {
        return guice;
    }

    private void logStartMessage() {
        log.info("==========");
        log.info("{} Server running version {}", getImplementationName(), getImplementationVersion());
        log.info("Implementing {} API version {}", getApiName(), getApiVersion());
        log.info("==========");
        log.info("Starting server...");
    }

    public static void main(String[] args) {
        new JungleServer();
    }

    @Override
    public String getImplementationName() {
        return JungleServer.class.getPackage().getImplementationTitle();
    }

    @Override
    public String getImplementationVersion() {
        return JungleServer.class.getPackage().getImplementationVersion();
    }
}
