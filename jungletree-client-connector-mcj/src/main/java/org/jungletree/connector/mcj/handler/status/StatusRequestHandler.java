package org.jungletree.connector.mcj.handler.status;

import com.flowpowered.network.MessageHandler;
import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.codec.status.ServerListPingResponseObject;
import org.jungletree.connector.mcj.codec.status.ServerListPingResponseObject.SLVersion;
import org.jungletree.connector.mcj.config.JClientConnectorResourceService;
import org.jungletree.connector.mcj.message.status.StatusRequestMessage;
import org.jungletree.connector.mcj.message.status.StatusResponseMessage;
import org.jungletree.network.ClientConnectorResourceService;

import java.util.Collections;

import static org.jungletree.connector.mcj.codec.status.ServerListPingResponseObject.SLDescription;
import static org.jungletree.connector.mcj.codec.status.ServerListPingResponseObject.SLPlayers;

public class StatusRequestHandler implements MessageHandler<JSession, StatusRequestMessage> {

    private final ClientConnectorResourceService resource;

    public StatusRequestHandler(ClientConnectorResourceService resource) {
        this.resource = resource;
    }

    @Override
    public void handle(JSession session, StatusRequestMessage message) {
        ServerListPingResponseObject responseObject = new ServerListPingResponseObject();

        responseObject.setVersion(SLVersion.create(((JClientConnectorResourceService)resource).getGameVersion()));

        responseObject.setPlayers(SLPlayers.create(
                resource.getMaxPlayers(),
                0, // event.getOnlinePlayers().size(),
                0, // event.getServerListSampleSize(),
                Collections.emptySet() // event.getOnlinePlayers()
        ));

        responseObject.setDescription(SLDescription.create(resource.getServerDescription()));

        session.send(new StatusResponseMessage(responseObject));
    }
}
