package im.octo.jungletree.network.message.entity;

import com.flowpowered.network.Message;

public class SpawnExperienceOrbMessage implements Message {

    private final int id;
    private final double x;
    private final double y;
    private final double z;
    private final short count;

    public SpawnExperienceOrbMessage(int id, double x, double y, double z, short count) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public short getCount() {
        return count;
    }
}
