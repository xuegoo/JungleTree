package org.jungletree.connector.mcj.handler.login.model;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class PlayerProfile {

    private static final int MAX_USERNAME_LENGTH = 16;

    private final UUID uniqueId;
    private final String name;
    private final List<PlayerProperty> properties;

    public PlayerProfile(UUID uniqueId, String name) {
        this(uniqueId, name, Collections.emptyList());
    }

    public PlayerProfile(UUID uniqueId, String name, List<PlayerProperty> properties) {
        checkNotNull("UUID cannot be null");
        checkNotNull(properties, "Properties cannot be null");

        this.uniqueId = uniqueId;
        this.name = name;
        this.properties = properties;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }
}
