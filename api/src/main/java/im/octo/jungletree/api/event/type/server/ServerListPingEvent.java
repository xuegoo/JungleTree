package im.octo.jungletree.api.event.type.server;

import im.octo.jungletree.api.entity.Player;

import java.net.InetAddress;
import java.util.Iterator;

public class ServerListPingEvent implements ServerEvent, Iterable<Player> {

    private final InetAddress address;
    private String description;
    private final int numPlayers;
    private int maxPlayers;

    public ServerListPingEvent(final InetAddress address, final String description, final int numPlayers, final int maxPlayers) {
        this.address = address;
        this.description = description;
        this.numPlayers = numPlayers;
        this.maxPlayers = maxPlayers;
    }

    protected ServerListPingEvent(final InetAddress address, final String description, final int maxPlayers) {
        this.numPlayers = 0;
        this.address = address;
        this.description = description;
        this.maxPlayers = maxPlayers;
    }

    @Override
    public String getName() {
        return ServerListPingEvent.class.getSimpleName();
    }

    @Override
    public Type getType() {
        return Type.NETWORK;
    }

    public InetAddress getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlayerCount() {
        return numPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public Iterator<Player> iterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
