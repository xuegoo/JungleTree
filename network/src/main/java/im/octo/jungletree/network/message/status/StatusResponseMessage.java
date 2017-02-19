package im.octo.jungletree.network.message.status;

import com.flowpowered.network.Message;
import im.octo.jungletree.network.codec.status.ServerListPingResponseObject;

public class StatusResponseMessage implements Message {

    private final ServerListPingResponseObject responseObject;

    public StatusResponseMessage(ServerListPingResponseObject responseObject) {
        this.responseObject = responseObject;
    }

    public ServerListPingResponseObject getResponseObject() {
        return responseObject;
    }
}
