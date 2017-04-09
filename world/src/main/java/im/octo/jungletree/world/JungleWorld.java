package im.octo.jungletree.world;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.world.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "worlds", indexes = {
        @Index(name = "idx_uuid_name", columnList = "uuid,name"),
})
public class JungleWorld implements World {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID uuid;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Dimension dimension;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "world_chunks",
            joinColumns = {@JoinColumn(name="world_uuid")},
            inverseJoinColumns = {@JoinColumn(name="chunk_uuid")} )
    @MapKey(name = "uuid")
    private final Map<UUID, JungleChunk> chunks = new ConcurrentHashMap<>();

    private transient final Set<Player> players = Collections.synchronizedSet(new HashSet<>());

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public Chunk getChunkAt(Location location) {
        return null;
    }

    @Override
    public Block getBlockAt(Location location) {
        return null;
    }

    @Override
    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(this, 0, 0, 0);
    }
}
