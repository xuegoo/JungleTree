package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class ResourcePackStatusMessage implements Message {

    private final int result;

    public ResourcePackStatusMessage(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }
}
