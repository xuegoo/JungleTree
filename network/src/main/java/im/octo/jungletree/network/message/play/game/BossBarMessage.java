package im.octo.jungletree.network.message.play.game;

import com.flowpowered.network.Message;

public class BossBarMessage implements Message {

    private final String uuid;
    private final int actionId;
    private final String title;
    private final float health;
    private final int colorId;
    private final int divisionType;
    private final int flags;

    public BossBarMessage(String uuid, int actionId, String title, float health, int colorId, int divisionType, int flags) {
        this.uuid = uuid;
        this.actionId = actionId;
        this.title = title;
        this.health = health;
        this.colorId = colorId;
        this.divisionType = divisionType;
        this.flags = flags;
    }

    public String getUuid() {
        return uuid;
    }

    public int getActionId() {
        return actionId;
    }

    public String getTitle() {
        return title;
    }

    public float getHealth() {
        return health;
    }

    public int getColorId() {
        return colorId;
    }

    public int getDivisionType() {
        return divisionType;
    }

    public int getFlags() {
        return flags;
    }
}
