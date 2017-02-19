package network.message.status;

import com.flowpowered.network.AsyncableMessage;

public class StatusRequestMessage implements AsyncableMessage {

    @Override
    public boolean isAsync() {
        return true;
    }
}
