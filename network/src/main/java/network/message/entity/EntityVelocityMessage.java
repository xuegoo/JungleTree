package network.message.entity;

import com.flowpowered.network.Message;

public class EntityVelocityMessage implements Message {

    private final int id;
    private final int velocityX;
    private final int velocityY;
    private final int velocityZ;

    public EntityVelocityMessage(int id, int velocityX, int velocityY, int velocityZ) {
        this.id = id;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    // TODO: Vectors
    /* public EntityVelocityMessage(int id, Vector velocity) {
        this(id, convert(velocity.getX()), convert(velocity.getY()), convert(velocity.getZ()));
    }*/

    private static int convert(double value) {
        return (int) (value * 8000);
    }
}
