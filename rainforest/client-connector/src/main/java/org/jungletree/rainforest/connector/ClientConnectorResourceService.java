package org.jungletree.rainforest.connector;

import java.security.KeyPair;

public interface ClientConnectorResourceService {

    int getPort();

    String getGameVersion();

    int getProtocolVersion();

    KeyPair getKeyPair();

    int getMaxPlayers();

    String getServerDescription();

    int getCompressionThreshold();
}
