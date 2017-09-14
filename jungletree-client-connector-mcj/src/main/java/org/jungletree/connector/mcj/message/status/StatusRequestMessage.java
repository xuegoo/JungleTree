package org.jungletree.connector.mcj.message.status;

import com.flowpowered.network.AsyncableMessage;

public class StatusRequestMessage implements AsyncableMessage {

    @Override
    public boolean isAsync() {
        return true;
    }
}
