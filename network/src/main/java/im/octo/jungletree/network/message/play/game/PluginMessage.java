package im.octo.jungletree.network.message.play.game;

import com.flowpowered.network.Message;

public class PluginMessage implements Message {

    private final String channel;
    private final byte[] message;

    public PluginMessage(String channel, byte[] message) {
        this.channel = channel;
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public byte[] getMessage() {
        return message;
    }
}
