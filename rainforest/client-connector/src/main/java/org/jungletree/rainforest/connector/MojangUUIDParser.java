package org.jungletree.rainforest.connector;

import java.util.UUID;

public final class MojangUUIDParser {

    private MojangUUIDParser() {}

    public static UUID fromStrippedUUID(String uuid) {
        try {
            return new UUID(Long.parseUnsignedLong(uuid.substring(0, 16), 16), Long.parseUnsignedLong(uuid.substring(16), 16));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid UUID");
        }
    }
}
