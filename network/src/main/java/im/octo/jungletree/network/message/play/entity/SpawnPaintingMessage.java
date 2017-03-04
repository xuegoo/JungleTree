package im.octo.jungletree.network.message.play.entity;

import com.flowpowered.network.Message;

public class SpawnPaintingMessage implements Message {

    private final int entityId;
    private final String entityUuid;
    private final String title;
    private final int x;
    private final int y;
    private final int z;
    private final int direction;

    public SpawnPaintingMessage(int entityId, String entityUuid, String title, int x, int y, int z, int direction) {
        this.entityId = entityId;
        this.entityUuid = entityUuid;
        this.title = title;
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
    }

    public int getEntityId() {
        return entityId;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public String getTitle() {
        return title;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getDirection() {
        return direction;
    }
}
