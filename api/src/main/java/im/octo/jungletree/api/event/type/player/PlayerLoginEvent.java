package im.octo.jungletree.api.event.type.player;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.network.LoginResult;

import java.net.InetAddress;

public class PlayerLoginEvent extends PlayerEvent {

    private final InetAddress address;
    private final String hostname;
    private LoginResult loginResult = LoginResult.ALLOWED;
    private String kickMessage = "";

    public PlayerLoginEvent(Player player, InetAddress address, String hostname) {
        super(player);
        this.address = address;
        this.hostname = hostname;
        this.loginResult = LoginResult.ALLOWED;
        this.kickMessage = "";
    }

    @Override
    public String getName() {
        return PlayerLoginEvent.class.getSimpleName();
    }

    public InetAddress getAddress() {
        return address;
    }

    public String getHostname() {
        return hostname;
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

    public void allow() {
        loginResult = LoginResult.ALLOWED;
        kickMessage = "";
    }

    public void disallow(LoginResult result, String reason) {
        this.loginResult = loginResult;
        this.kickMessage = reason;
    }
}
