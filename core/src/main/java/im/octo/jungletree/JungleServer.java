package im.octo.jungletree;

import com.google.inject.Guice;
import com.google.inject.Injector;
import im.octo.jungletree.api.GameVersion;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.scheduler.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;

public class JungleServer implements Server {

    private static final Logger log = LoggerFactory.getLogger(JungleServer.class);

    private static final GameVersion GAME_VERSION = GameVersion.MC_1_11_2;

    private final Injector guice;
    private final TaskScheduler scheduler;

    private JungleServer() {
        this.guice = Guice.createInjector(new JungleGuiceModule());
        this.scheduler = guice.getInstance(TaskScheduler.class);

        scheduler.execute(this::logStartMessage);
        scheduler.shutdown();
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

    @Override
    public GameVersion getGameVersion() {
        return GAME_VERSION;
    }

    @Override
    public byte[] getFavicon() {
        return new byte[0];
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Collection<Player> getOnlinePlayers() {
        return Collections.emptySet();
    }

    @Override
    public int getMaxOnlinePlayers() {
        return 0;
    }

    @Override
    public int getServerListSampleSize() {
        return 0;
    }

    @Override
    public Injector getGuice() {
        return guice;
    }
}
