package network.message.entity;

import com.flowpowered.network.Message;

public class EntityRotationMessage implements Message {

    private final int id;
    private final int yaw;
    private final int pitch;
    private final boolean grounded;

    public EntityRotationMessage(int id, int yaw, int pitch, boolean grounded) {
        this.id = id;
        this.yaw = yaw;
        this.pitch = pitch;
        this.grounded = grounded;
    }

    public EntityRotationMessage(int id, int yaw, int pitch) {
        this(id, yaw, pitch, true);
    }

    public int getId() {
        return id;
    }

    public int getYaw() {
        return yaw;
    }

    public int getPitch() {
        return pitch;
    }

    public boolean isGrounded() {
        return grounded;
    }
}
