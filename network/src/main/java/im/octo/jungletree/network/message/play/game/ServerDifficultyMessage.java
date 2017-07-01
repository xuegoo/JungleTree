package im.octo.jungletree.network.message.play.game;

import com.flowpowered.network.Message;

public class ServerDifficultyMessage implements Message {

    private final int difficulty;

    public ServerDifficultyMessage(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
