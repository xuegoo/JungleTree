package org.jungletree.connector.mcj.config;

import org.jungletree.connector.mcj.SecurityUtils;
import org.jungletree.network.ClientConnectorResourceService;

import javax.inject.Singleton;
import java.security.KeyPair;

@Singleton
public class JClientConnectorResourceService implements ClientConnectorResourceService {

    private final int port = 25565;
    private final GameVersion gameVersion = GameVersion.MC_1_12_2;

    private final KeyPair keyPair = SecurityUtils.generateKeyPair();

    private final int maxPlayers = 1000;
    private final String serverDescription = "Hello, world.";

    public int getPort() {
        return port;
    }

    public GameVersion getGameVersion() {
        return gameVersion;
    }

    public int getProtocolVersion() {
        return gameVersion.getProtocolVersion();
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getServerDescription() {
        return serverDescription;
    }
}
