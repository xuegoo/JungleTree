package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class SpawnPositionMessage implements Message {

    private final int x;
    private final int y;
    private final int z;

    public SpawnPositionMessage(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
}
