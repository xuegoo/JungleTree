package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.util.Vector;

public class BlockActionMessage implements Message {

    private final Vector location;
    private final int actionId;
    private final int actionParam;
    private final int blockType;

    public BlockActionMessage(Vector location, int actionId, int actionParam, int blockType) {
        this.location = location;
        this.actionId = actionId;
        this.actionParam = actionParam;
        this.blockType = blockType;
    }

    public Vector getLocation() {
        return location;
    }

    public int getActionId() {
        return actionId;
    }

    public int getActionParam() {
        return actionParam;
    }

    public int getBlockType() {
        return blockType;
    }
}
