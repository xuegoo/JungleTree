package im.octo.jungletree.rainforest.user;

import java.util.UUID;

public interface User {

    UUID getUuid();

    String getUsername();

    boolean isOnline();

    void chat(String message);
}
