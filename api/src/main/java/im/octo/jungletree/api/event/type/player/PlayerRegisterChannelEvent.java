package im.octo.jungletree.api.event.type.player;

import im.octo.jungletree.api.event.Event;

public class PlayerRegisterChannelEvent implements Event {

    private final String channel;

    public PlayerRegisterChannelEvent(String channel) {
        this.channel = channel;
    }

    @Override
    public String getName() {
        return PlayerRegisterChannelEvent.class.getSimpleName();
    }

    public String getChannel() {
        return channel;
    }
}
