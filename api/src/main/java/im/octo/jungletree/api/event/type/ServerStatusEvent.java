package im.octo.jungletree.api.event.type;

import im.octo.jungletree.api.GameVersion;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.event.Event;

import java.net.InetAddress;
import java.util.Collection;
import java.util.List;

public class ServerStatusEvent implements Event {

    private final InetAddress address;
    private GameVersion gameVersion;
    private Collection<Player> onlinePlayers;
    private int maxPlayers;
    private int serverListSampleSize;
    private String description;
    private byte[] favicon;

    public ServerStatusEvent(InetAddress address, GameVersion gameVersion, Collection<Player> onlinePlayers, int maxPlayers, int serverListSampleSize, String description, byte[] favicon) {
        this.address = address;
        this.gameVersion = gameVersion;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.serverListSampleSize = serverListSampleSize;
        this.description = description;
        this.favicon = favicon;
    }

    @Override
    public String getName() {
        return ServerStatusEvent.class.getSimpleName();
    }

    @Override
    public Type getType() {
        return Type.NETWORK;
    }

    public InetAddress getAddress() {
        return address;
    }

    public GameVersion getGameVersion() {
        return gameVersion;
    }

    public Collection<Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(List<Player> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getServerListSampleSize() {
        return serverListSampleSize;
    }

    public void setServerListSampleSize(int serverListSampleSize) {
        this.serverListSampleSize = serverListSampleSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFavicon() {
        return favicon;
    }

    public void setFavicon(byte[] favicon) {
        this.favicon = favicon;
    }
}
