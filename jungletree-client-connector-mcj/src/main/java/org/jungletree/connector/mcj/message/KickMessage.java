package org.jungletree.connector.mcj.message;

import com.flowpowered.network.Message;

public class KickMessage implements Message {

    private final String text;

    public KickMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
