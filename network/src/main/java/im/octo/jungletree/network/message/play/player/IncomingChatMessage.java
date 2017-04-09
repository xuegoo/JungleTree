package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.AsyncableMessage;

public class IncomingChatMessage implements AsyncableMessage {

    private final String message;

    public IncomingChatMessage(String message) {
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
