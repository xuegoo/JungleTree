package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class SpectateMessage implements Message {

    private final String targetEntityUuid;

    public SpectateMessage(String targetEntityUuid) {
        this.targetEntityUuid = targetEntityUuid;
    }

    public String getTargetEntityUuid() {
        return targetEntityUuid;
    }
}
