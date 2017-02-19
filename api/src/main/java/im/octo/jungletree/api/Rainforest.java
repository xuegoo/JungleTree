package im.octo.jungletree.api;

import im.octo.jungletree.api.exception.IllegalOperationException;

public final class Rainforest {

    private static Server server;

    public static Server getServer() {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server;
    }

    public static void setServer(Server server) {
        if (Rainforest.server != null) {
            throw new IllegalOperationException("Server is a singleton object and cannot be redefined");
        }
        Rainforest.server = server;
    }
}
