package im.octo.jungletree.network.message.play.inventory;

import com.flowpowered.network.Message;

public class UpdateSignMessage implements Message {

    private final int x;
    private final int y;
    private final int z;
    private final String line1;
    private final String line2;
    private final String line3;
    private final String line4;

    public UpdateSignMessage(int x, int y, int z, String line1, String line2, String line3, String line4) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
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

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getLine3() {
        return line3;
    }

    public String getLine4() {
        return line4;
    }
}
