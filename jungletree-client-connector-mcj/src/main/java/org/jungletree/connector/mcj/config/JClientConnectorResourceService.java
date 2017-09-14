package org.jungletree.connector.mcj.config;

import org.jungletree.connector.mcj.SecurityUtils;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.util.GameVersion;

import javax.inject.Singleton;
import java.security.KeyPair;

@Singleton
public class JClientConnectorResourceService implements ClientConnectorResourceService {

    private final int port = 25565;
    private final GameVersion gameVersion = GameVersion.MC_1_12_1;

    private final KeyPair keyPair = SecurityUtils.generateKeyPair();

    private final int maxPlayers = 1000;
    private final String serverDescription = "Hello, world.";
    private final int compressionThreshold = 0;

    @Override
    public int getPort() {
        return port;
    }

    public String getGameVersion() {
        return gameVersion.getVersionName();
    }

    @Override
    public int getProtocolVersion() {
        return gameVersion.getProtocolVersion();
    }

    @Override
    public KeyPair getKeyPair() {
        return keyPair;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public String getServerDescription() {
        return serverDescription;
    }

    @Override
    public int getCompressionThreshold() {
        return compressionThreshold;
    }
}
