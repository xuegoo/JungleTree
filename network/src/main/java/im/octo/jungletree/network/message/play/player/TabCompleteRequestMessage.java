package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.util.BlockVector;

public class TabCompleteRequestMessage implements Message {

    private final String text;
    private final boolean assumeCommand;
    private final BlockVector location;

    public TabCompleteRequestMessage(String text, boolean assumeCommand, BlockVector location) {
        this.text = text;
        this.assumeCommand = assumeCommand;
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public boolean isAssumeCommand() {
        return assumeCommand;
    }

    public BlockVector getLocation() {
        return location;
    }
}
