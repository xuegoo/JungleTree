package org.jungletree.connector.mcj.message.login;

import com.flowpowered.network.Message;

public class LoginSuccessMessage implements Message {

    private final String uuid;
    private final String username;

    public LoginSuccessMessage(String uuid, String username) {
        this.uuid = uuid;
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }
}
