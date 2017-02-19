package im.octo.jungletree.network;

import im.octo.jungletree.api.player.meta.PlayerProfile;
import im.octo.jungletree.api.player.meta.PlayerProperty;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

public class ProxyData {

    private String securityKey;
    private String hostname;
    private InetSocketAddress address;
    private UUID uuid;
    private String username;
    private List<PlayerProperty> properties;

    public ProxyData(String securityKey, String hostname, InetSocketAddress address, UUID uuid, String username, List<PlayerProperty> properties) {
        this.securityKey = securityKey;
        this.hostname = hostname;
        this.address = address;
        this.uuid = uuid;
        this.username = username;
        this.properties = properties;
    }

    public ProxyData(JSession session, String sourceText) throws Exception {
        // TODO: Implement
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public PlayerProfile getProfile() {
        if (username == null) {
            return null;
        }
        return new PlayerProfile(uuid, username, properties);
    }

    public PlayerProfile getProfile(String username) {
        return new PlayerProfile(uuid, username, properties);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PlayerProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<PlayerProperty> properties) {
        this.properties = properties;
    }
}
