package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class BlockPlaceMessage implements Message {

    private final int x;
    private final int y;
    private final int z;
    private final int blockFace;
    private final int hand;
    private final float cursorX;
    private final float cursorY;
    private final float cursorZ;

    public BlockPlaceMessage(int x, int y, int z, int blockFace, int hand, float cursorX, float cursorY, float cursorZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockFace = blockFace;
        this.hand = hand;
        this.cursorX = cursorX;
        this.cursorY = cursorY;
        this.cursorZ = cursorZ;
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

    public int getBlockFace() {
        return blockFace;
    }

    public int getHand() {
        return hand;
    }

    public float getCursorX() {
        return cursorX;
    }

    public float getCursorY() {
        return cursorY;
    }

    public float getCursorZ() {
        return cursorZ;
    }
}
