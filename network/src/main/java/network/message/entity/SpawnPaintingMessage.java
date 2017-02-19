package network.message.entity;

import com.flowpowered.network.Message;

public class SpawnPaintingMessage implements Message {

    private final int id;
    private final String title;
    private final int x;
    private final int y;
    private final int z;
    private final int facing;

    public SpawnPaintingMessage(int id, String title, int x, int y, int z, int facing) {
        this.id = id;
        this.title = title;
        this.x = x;
        this.y = y;
        this.z = z;
        this.facing = facing;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getFacing() {
        return facing;
    }
}
