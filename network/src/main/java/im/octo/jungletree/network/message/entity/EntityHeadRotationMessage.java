package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

public class EntityHeadRotationMessage implements Message {

    private final int id;
    private final int yaw;

    public EntityHeadRotationMessage(int id, int yaw) {
        this.id = id;
        this.yaw = yaw;
    }

    public int getId() {
        return id;
    }

    public int getYaw() {
        return yaw;
    }
}
