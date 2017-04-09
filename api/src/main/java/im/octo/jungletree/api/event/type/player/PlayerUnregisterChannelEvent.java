package im.octo.jungletree.api.event.type.player;

import im.octo.jungletree.api.event.Event;

public class PlayerUnregisterChannelEvent implements Event {

    private final String channel;

    public PlayerUnregisterChannelEvent(String channel) {
        this.channel = channel;
    }

    @Override
    public String getName() {
        return PlayerUnregisterChannelEvent.class.getSimpleName();
    }

    public String getChannel() {
        return channel;
    }
}
