package im.octo.jungletree.api.player.meta;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class PlayerProfile {

    private static final int MAX_USERNAME_LENGTH = 16;
    private final UUID uuid;
    private final String username;
    private final Collection<PlayerProperty> properties;

    public PlayerProfile(UUID uuid, String username) {
        this(uuid, username, Collections.emptyList());
    }

    public PlayerProfile(UUID uuid, String username, Collection<PlayerProperty> properties) {
        this.uuid = uuid;
        this.username = username;
        this.properties = properties;
    }

    public static int getMaxUsernameLength() {
        return MAX_USERNAME_LENGTH;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public Collection<PlayerProperty> getProperties() {
        return properties;
    }

    // TODO: Static conversion methods?
}
