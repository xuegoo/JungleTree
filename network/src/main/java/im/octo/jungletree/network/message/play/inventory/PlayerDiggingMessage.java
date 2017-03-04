package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class PlayerDiggingMessage implements Message {

    private final int status;
    private final int blockX;
    private final int blockY;
    private final int blockZ;
    private final int blockFace;

    public PlayerDiggingMessage(int status, int blockX, int blockY, int blockZ, int blockFace) {
        this.status = status;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.blockFace = blockFace;
    }

    public int getStatus() {
        return status;
    }

    public int getBlockX() {
        return blockX;
    }

    public int getBlockY() {
        return blockY;
    }

    public int getBlockZ() {
        return blockZ;
    }

    public int getBlockFace() {
        return blockFace;
    }
}
