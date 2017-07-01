package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class JoinGameMessage implements Message {

    private final int id;
    private final int mode;
    private final int dimension;
    private final int difficulty;
    private final int maxPlayers;
    private final String levelType;
    private final boolean reducedDebugInfo;

    public JoinGameMessage(int id, int mode, int dimension, int difficulty, int maxPlayers, String levelType, boolean reducedDebugInfo) {
        this.id = id;
        this.mode = mode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.levelType = levelType;
        this.reducedDebugInfo = reducedDebugInfo;
    }

    public int getId() {
        return id;
    }

    public int getMode() {
        return mode;
    }

    public int getDimension() {
        return dimension;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getLevelType() {
        return levelType;
    }

    public boolean isReducedDebugInfo() {
        return reducedDebugInfo;
    }
}
