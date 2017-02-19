package im.octo.jungletree.network.handler.status;

import com.flowpowered.network.MessageHandler;
import com.google.inject.Injector;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.type.server.ServerStatusEvent;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.codec.status.ServerListPingResponseObject;
import im.octo.jungletree.network.codec.status.ServerListPingResponseObject.SLVersion;
import im.octo.jungletree.network.message.status.StatusRequestMessage;
import im.octo.jungletree.network.message.status.StatusResponseMessage;

import java.util.Collections;

import static im.octo.jungletree.network.codec.status.ServerListPingResponseObject.SLDescription;
import static im.octo.jungletree.network.codec.status.ServerListPingResponseObject.SLPlayers;

public class StatusRequestHandler implements MessageHandler<JSession, StatusRequestMessage> {

    @Override
    public void handle(JSession session, StatusRequestMessage message) {
        Server server = Rainforest.getServer();

        ServerStatusEvent event = new ServerStatusEvent(
                session.getAddress().getAddress(),
                server.getGameVersion(),
                Collections.unmodifiableCollection(server.getOnlinePlayers()),
                server.getMaxOnlinePlayers(),
                server.getServerListSampleSize(),
                server.getDescription(),
                server.getFavicon()
        );

        Injector guice = server.getGuice();
        EventService eventService = guice.getInstance(EventService.class);
        eventService.call(event);

        ServerListPingResponseObject responseObject = createResponse(event);
        session.send(new StatusResponseMessage(responseObject));
    }

    private ServerListPingResponseObject createResponse(ServerStatusEvent event) {
        ServerListPingResponseObject result = new ServerListPingResponseObject();

        result.setVersion(
                SLVersion.create(event.getGameVersion())
        );

        result.setPlayers(
                SLPlayers.create(
                        event.getMaxPlayers(),
                        event.getOnlinePlayers().size(),
                        event.getServerListSampleSize(),
                        event.getOnlinePlayers()
                )
        );

        result.setDescription(
                SLDescription.create(event.getDescription())
        );

        result.setFaviconBytes(event.getFavicon());
        return result;
    }
}
