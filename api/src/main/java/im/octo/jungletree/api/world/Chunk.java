package im.octo.jungletree.api.world;

import java.util.UUID;

public interface Chunk {

    UUID getUuid();

    int getX();

    int getZ();
}
