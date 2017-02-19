package network.message.entity;

import com.flowpowered.network.Message;

public class SetPassengerMessage implements Message {

    private final int entityId;
    private final int[] passengers;

    public SetPassengerMessage(int entityId, int[] passengers) {
        this.entityId = entityId;
        this.passengers = passengers;
    }

    public int getEntityId() {
        return entityId;
    }

    public int[] getPassengers() {
        return passengers;
    }
}
