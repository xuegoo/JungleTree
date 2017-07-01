package im.octo.jungletree.network.message.status;

import com.flowpowered.network.AsyncableMessage;

public class StatusPingMessage implements AsyncableMessage {

    private final long time;

    public StatusPingMessage(long time) {
        this.time = time;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    public long getTime() {
        return time;
    }
}