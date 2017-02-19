package im.octo.jungletree.api;

import com.google.inject.Injector;
import im.octo.jungletree.api.entity.Player;

import java.security.KeyPair;
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

    int getCompressionThreshold();

    Injector getGuice();

    KeyPair getKeyPair();

    boolean isOnlineMode();

    void setIp(String host);

    int getPort();

    void setPort(int port);

    void broadcastMessage(String message);
}
