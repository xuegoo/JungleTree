package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class ClientKeepAliveMessage implements Message {

    private final int keepAliveId;

    public ClientKeepAliveMessage(int keepAliveId) {
        this.keepAliveId = keepAliveId;
    }

    public int getKeepAliveId() {
        return keepAliveId;
    }
}
