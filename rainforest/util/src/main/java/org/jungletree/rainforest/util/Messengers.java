package org.jungletree.rainforest.util;

import java.util.UUID;

public final class Messengers {

    private Messengers() {}

    public static final String CLIENT_CONNECTOR = "CLIENT_CONNECTOR";
    public static final String WORLD_STORAGE = "WORLD_STORAGE";

    public static String ident(String messengerName) {
        return messengerName + "_" + UUID.randomUUID();
    }
}
