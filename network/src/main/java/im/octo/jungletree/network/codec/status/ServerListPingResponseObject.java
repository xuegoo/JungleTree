package im.octo.jungletree.network.codec.status;

import im.octo.jungletree.api.GameVersion;
import im.octo.jungletree.rainforest.minecraft.entity.Player;

import java.io.Serializable;
import java.util.*;

public final class ServerListPingResponseObject implements Serializable {

    private static final String FAVICON_DATA_HEADER = "data:image/png;base64,";

    private SLVersion version;
    private SLPlayers players;
    private SLDescription description;
    private String favicon;

    public SLVersion getVersion() {
        return version;
    }

    public void setVersion(SLVersion version) {
        this.version = version;
    }

    public SLPlayers getPlayers() {
        return players;
    }

    public void setPlayers(SLPlayers players) {
        this.players = players;
    }

    public SLDescription getDescription() {
        return description;
    }

    public void setDescription(SLDescription description) {
        this.description = description;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public byte[] getFaviconBytes() {
        return Base64.getDecoder().decode(favicon.substring(FAVICON_DATA_HEADER.length(), favicon.length()));
    }

    public void setFaviconBytes(byte[] bytes) {
        favicon = FAVICON_DATA_HEADER + Base64.getEncoder().encodeToString(bytes);
    }

    public static class SLVersion {
        private String name;
        private int protocol;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getProtocol() {
            return protocol;
        }

        public void setProtocol(int protocol) {
            this.protocol = protocol;
        }

        public static SLVersion create(GameVersion gameVersion) {
            SLVersion result = new SLVersion();
            result.setName(gameVersion.getName());
            result.setProtocol(gameVersion.getProtocolVersion());
            return result;
        }
    }

    public static class SLPlayers {
        private int max;
        private int online;
        private Collection<SLPlayer> sample;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public Collection<SLPlayer> getSample() {
            return sample;
        }

        public void setSample(Collection<SLPlayer> sample) {
            this.sample = sample;
        }

        public static SLPlayers create(int max, int online, int sampleSize, Collection<Player> onlinePlayers) {
            SLPlayers result = new SLPlayers();
            result.setMax(max);
            result.setOnline(online);

            Set<SLPlayer> sample = new HashSet<>();
            Iterator<Player> iterator = onlinePlayers.iterator();
            while(iterator.hasNext() && sample.size()<sampleSize) {
                sample.add(SLPlayer.create(iterator.next()));
            }
            result.setSample(sample);
            return result;
        }
    }

    public static class SLPlayer {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public UUID getUuid() {
            return UUID.fromString(id);
        }

        public void setUuid(UUID uuid) {
            this.id = uuid.toString().toLowerCase();
        }

        private static SLPlayer create(Player player) {
            SLPlayer result = new SLPlayer();
            result.setUuid(player.getUuid());
            result.setName(player.getName());
            return result;
        }
    }

    public static class SLDescription {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static SLDescription create(String text) {
            SLDescription result = new SLDescription();
            result.setText(text);
            return result;
        }
    }
}
