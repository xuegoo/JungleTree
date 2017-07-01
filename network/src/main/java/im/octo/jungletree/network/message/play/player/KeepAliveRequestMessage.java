package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class KeepAliveRequestMessage implements Message {

    private final int keepAliveId;

    public KeepAliveRequestMessage(int keepAliveId) {
        this.keepAliveId = keepAliveId;
    }

    public int getKeepAliveId() {
        return keepAliveId;
    }
}
