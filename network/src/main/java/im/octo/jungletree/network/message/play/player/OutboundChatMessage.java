package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class OutboundChatMessage implements Message {

    private final String json;
    private final int chatPosition;

    public OutboundChatMessage(String json, int chatPosition) {
        this.json = json;
        this.chatPosition = chatPosition;
    }

    public String getJson() {
        return json;
    }

    public int getChatPosition() {
        return chatPosition;
    }
}
