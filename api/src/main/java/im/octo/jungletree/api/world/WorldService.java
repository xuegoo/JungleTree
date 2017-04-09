package im.octo.jungletree.api.world;

import java.util.UUID;

public interface WorldService {

    World getWorld(UUID uuid);

    World getWorld(String name);
}
