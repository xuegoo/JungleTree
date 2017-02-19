package network.message.login;

import com.flowpowered.network.AsyncableMessage;

public class LoginStartMessage implements AsyncableMessage {

    private final String username;

    public LoginStartMessage(String username) {
        this.username = username;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    public String getUsername() {
        return username;
    }
}
