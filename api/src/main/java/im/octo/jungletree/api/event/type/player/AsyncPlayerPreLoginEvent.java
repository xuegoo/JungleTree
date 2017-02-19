package im.octo.jungletree.api.event.type.player;

import im.octo.jungletree.api.event.Event;
import im.octo.jungletree.api.network.LoginResult;

import java.net.InetSocketAddress;
import java.util.UUID;

public class AsyncPlayerPreLoginEvent implements Event {

    private LoginResult loginResult;
    private String kickMessage;
    private final UUID playerUuid;
    private final String username;
    private final InetSocketAddress address;

    public AsyncPlayerPreLoginEvent(UUID playerUuid, String username, InetSocketAddress address) {
        this.loginResult = LoginResult.ALLOWED;
        this.kickMessage = "";
        this.playerUuid = playerUuid;
        this.username = username;
        this.address = address;
    }

    @Override
    public String getName() {
        return AsyncPlayerPreLoginEvent.class.getSimpleName();
    }

    @Override
    public Type getType() {
        return Type.NETWORK;
    }

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    public String getKickMessage() {
        return kickMessage;
    }

    public void setKickMessage(String kickMessage) {
        this.kickMessage = kickMessage;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public String getUsername() {
        return username;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void allow() {
        loginResult = LoginResult.ALLOWED;
        kickMessage = "";
    }

    public void disallow(LoginResult result, String reason) {
        this.loginResult = loginResult;
        this.kickMessage = reason;
    }
}
