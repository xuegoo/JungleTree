package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class TabCompleteResponseMessage implements Message {

    private final int count;
    private final String[] matches;

    public TabCompleteResponseMessage(int count, String[] matches) {
        this.count = count;
        this.matches = matches;
    }

    public int getCount() {
        return count;
    }

    public String[] getMatches() {
        return matches;
    }
}
