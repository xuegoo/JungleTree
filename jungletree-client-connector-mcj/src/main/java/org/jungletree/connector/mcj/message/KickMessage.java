package org.jungletree.connector.mcj.message;

import com.flowpowered.network.Message;
import org.jungletree.rainforest.util.Text;

public class KickMessage implements Message {

    private final Text text;

    public KickMessage(String text) {
        this.text = Text.build(text);
    }

    public KickMessage(Text text) {
        this.text = text;
    }

    public Text getText() {
        return text;
    }
}
