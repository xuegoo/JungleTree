package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class UpdateBlockEntityMessage implements Message {

    private final int x;
    private final int y;
    private final int z;
    private final int actionId;
}
