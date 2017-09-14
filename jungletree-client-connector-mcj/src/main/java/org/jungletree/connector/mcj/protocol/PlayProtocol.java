package org.jungletree.connector.mcj.protocol;

import org.jungletree.network.ClientConnectorResourceService;

import javax.inject.Inject;

public class PlayProtocol extends JProtocol {

    @Inject
    public PlayProtocol(ClientConnectorResourceService resource) {
        super("PLAY", 0x4B, resource);
    }
}
