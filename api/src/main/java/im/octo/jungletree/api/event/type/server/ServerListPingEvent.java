package im.octo.jungletree.api.event.type.server;

import im.octo.jungletree.api.entity.Player;

import java.net.InetAddress;
import java.util.Iterator;

public class ServerListPingEvent implements ServerEvent, Iterable<Player> {

    private static final int MAGIC_PLAYER_COUNT = Integer.MIN_VALUE;

    private final InetAddress address;
    private String motd;
    private final int numPlayers;
    private int maxPlayers;

    public ServerListPingEvent(final InetAddress address, final String motd, final int numPlayers, final int maxPlayers) {
        // Validate.isTrue(numPlayers >= 0, "Cannot have negative number of players online", numPlayers);
        this.address = address;
        this.motd = motd;
        this.numPlayers = numPlayers;
        this.maxPlayers = maxPlayers;
    }

    protected ServerListPingEvent(final InetAddress address, final String motd, final int maxPlayers) {
        this.numPlayers = MAGIC_PLAYER_COUNT;
        this.address = address;
        this.motd = motd;
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
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public int getPlayerCount() {
        int numPlayers = this.numPlayers;
        if (numPlayers == MAGIC_PLAYER_COUNT) {
            numPlayers = 0;
            for (@SuppressWarnings("unused") final Player player : this) {
                numPlayers++;
            }
        }
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
