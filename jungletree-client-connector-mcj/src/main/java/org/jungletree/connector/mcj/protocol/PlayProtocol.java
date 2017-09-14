package org.jungletree.connector.mcj.protocol;

import org.jungletree.connector.mcj.codec.KickCodec;
import org.jungletree.connector.mcj.message.KickMessage;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.scheduler.SchedulerService;

import javax.inject.Inject;

public class PlayProtocol extends JProtocol {

    @Inject
    public PlayProtocol(ClientConnectorResourceService resource, SchedulerService scheduler) {
        super("PLAY", 0x4B, resource, scheduler);

        outbound(0x1A, KickMessage.class, KickCodec.class);
    }
}
