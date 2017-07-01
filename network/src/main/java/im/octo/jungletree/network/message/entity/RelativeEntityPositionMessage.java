package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

public class RelativeEntityPositionMessage implements Message {

    private final int id;
    private final short deltaX;
    private final short deltaY;
    private final short deltaZ;
    private final boolean grounded;

    public RelativeEntityPositionMessage(int id, short deltaX, short deltaY, short deltaZ, boolean grounded) {
        this.id = id;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.grounded = grounded;
    }

    public RelativeEntityPositionMessage(int id, short deltaX, short deltaY, short deltaZ) {
        this(id, deltaX, deltaY, deltaZ, true);
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

    public boolean isGrounded() {
        return grounded;
    }
}
