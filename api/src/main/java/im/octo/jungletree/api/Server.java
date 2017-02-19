package im.octo.jungletree.api;

import com.google.inject.Injector;
import im.octo.jungletree.api.entity.Player;

import java.util.Collection;

public interface Server {

    default String getApiName() {
        return Server.class.getPackage().getSpecificationTitle();
    }

    default String getApiVersion() {
        return Server.class.getPackage().getSpecificationVersion();
    }

    String getImplementationName();

    String getImplementationVersion();

    GameVersion getGameVersion();

    byte[] getFavicon();

    String getDescription();

    Collection<Player> getOnlinePlayers();

    int getMaxOnlinePlayers();

    int getServerListSampleSize();

    Injector getGuice();
}
