package im.octo.jungletree.network.metadata;

import im.octo.jungletree.api.metadata.ClientSettings;
import im.octo.jungletree.network.message.play.player.ClientSettingsMessage;

public class JClientSettings implements ClientSettings {

    public static final JClientSettings DEFAULT = new JClientSettings("en_US", 8, 0, true, 127, 0);

    public static final int CHAT_ENABLED = 0;
    public static final int CHAT_COMMANDS_ONLY = 1;
    public static final int CHAT_HIDDEN = 2;

    public static final int SKIN_CAPE = 1;
    public static final int SKIN_JACKET = 1 << 1;
    public static final int SKIN_LEFT_SLEEVE = 1 << 2;
    public static final int SKIN_RIGHT_SLEEVE = 1 << 3;
    public static final int SKIN_LEFT_PANTS = 1 << 4;
    public static final int SKIN_RIGHT_PANTS = 1 << 5;
    public static final int SKIN_HAT = 1 << 6;

    private final String locale;
    private final int viewDistance;
    private final int chatFlags;
    private final boolean chatColors;
    private final int skinFlags;
    private final int mainHand;

    public JClientSettings(ClientSettingsMessage msg) {
        this(msg.getLocale(), msg.getRenderDistance(), msg.getChatMode(), msg.isChatColors(), msg.getSkinFlags(), msg.getMainHand());
    }

    public JClientSettings(String locale, int viewDistance, int chatFlags, boolean chatColors, int skinFlags, int mainHand) {
        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatFlags = chatFlags;
        this.chatColors = chatColors;
        this.skinFlags = skinFlags;
        this.mainHand = mainHand;
    }

    public boolean showChat() {
        return chatFlags == CHAT_ENABLED;
    }

    public boolean showCommands() {
        return chatFlags != CHAT_HIDDEN;
    }

    public boolean showChatColors() {
        return chatColors;
    }

    public String getLocale() {
        return locale;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public int getChatFlags() {
        return chatFlags;
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
