package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.AsyncableMessage;

public class InboundChatMessage implements AsyncableMessage {

    private final String message;

    public InboundChatMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    public String getMessage() {
        return message;
    }
}
