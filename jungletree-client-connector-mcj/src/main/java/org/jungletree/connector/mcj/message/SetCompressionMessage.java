package org.jungletree.connector.mcj.message;

import com.flowpowered.network.Message;

public class SetCompressionMessage implements Message {

    private final int threshold;

    public SetCompressionMessage(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }
}
