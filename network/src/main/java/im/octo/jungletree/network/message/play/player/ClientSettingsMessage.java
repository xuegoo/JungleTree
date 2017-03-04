package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class ClientSettingsMessage implements Message {

    private final String locale;
    private final int renderDistance;
    private final int chatMode;
    private final boolean chatColors;
    private final int skinFlags;
    private final int mainHand;

    public ClientSettingsMessage(String locale, int renderDistance, int chatMode, boolean chatColors, int skinFlags, int mainHand) {
        this.locale = locale;
        this.renderDistance = renderDistance;
        this.chatMode = chatMode;
        this.chatColors = chatColors;
        this.skinFlags = skinFlags;
        this.mainHand = mainHand;
    }

    public String getLocale() {
        return locale;
    }

    public int getRenderDistance() {
        return renderDistance;
    }

    public int getChatMode() {
        return chatMode;
    }

    public boolean isChatColors() {
        return chatColors;
    }

    public int getSkinFlags() {
        return skinFlags;
    }

    public int getMainHand() {
        return mainHand;
    }
}
