package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

public class RelativeEntityPositionRotationMessage implements Message {

    private final int id;
    private final short deltaX;
    private final short deltaY;
    private final short deltaZ;
    private final int yaw;
    private final int pitch;
    private final boolean grounded;

    public RelativeEntityPositionRotationMessage(int id, short deltaX, short deltaY, short deltaZ, int yaw, int pitch, boolean grounded) {
        this.id = id;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.grounded = grounded;
    }

    public RelativeEntityPositionRotationMessage(int id, short deltaX, short deltaY, short deltaZ, int yaw, int pitch) {
        this(id, deltaX, deltaY, deltaZ, yaw, pitch, true);
    }

    public int getId() {
        return id;
    }

    public short getDeltaX() {
        return deltaX;
    }

    public short getDeltaY() {
        return deltaY;
    }

    public short getDeltaZ() {
        return deltaZ;
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
