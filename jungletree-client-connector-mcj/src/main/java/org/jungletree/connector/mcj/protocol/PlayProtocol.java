package org.jungletree.connector.mcj.protocol;

import org.jungletree.connector.mcj.codec.KickCodec;
import org.jungletree.connector.mcj.codec.play.game.JoinGameCodec;
import org.jungletree.connector.mcj.codec.play.game.KeepAliveCodec;
import org.jungletree.connector.mcj.handler.play.game.KeepAliveHandler;
import org.jungletree.connector.mcj.message.KickMessage;
import org.jungletree.connector.mcj.message.play.game.JoinGameMessage;
import org.jungletree.connector.mcj.message.play.game.KeepAliveMessage;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.scheduler.SchedulerService;

import javax.inject.Inject;

public class PlayProtocol extends JProtocol {

    @Inject
    public PlayProtocol(ClientConnectorResourceService resource, SchedulerService scheduler) {
        super("PLAY", 0x4B, resource, scheduler);

        inbound(0x0B, KeepAliveMessage.class, KeepAliveCodec.class, KeepAliveHandler.class);

        outbound(0x23, JoinGameMessage.class, JoinGameCodec.class);
        outbound(0x0B, KeepAliveMessage.class, KeepAliveCodec.class);
        outbound(0x1A, KickMessage.class, KickCodec.class);
    }
}
