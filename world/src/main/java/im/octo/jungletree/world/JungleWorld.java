package im.octo.jungletree.world;

import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.exception.IllegalOperationException;
import im.octo.jungletree.api.world.*;
import im.octo.jungletree.api.world.block.Block;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static im.octo.jungletree.world.JungleBlock.HASH_CODE_PREFIX;
import static im.octo.jungletree.world.JungleBlock.HASH_CODE_SEPARATOR;

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

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private long seed;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Dimension dimension;

    @Column
    private int maxHeight;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Map<Integer, JungleChunk> chunkMap = new ConcurrentHashMap<>();

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

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public int getSpawnChunksDiameter() {
        // TODO: Don't hardcode
        return 2;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public Chunk getChunkAt(Location location) {
        return null;
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {
        int coodinateHashCode = Objects.hash(HASH_CODE_PREFIX, chunkX, HASH_CODE_SEPARATOR, chunkZ);
        if (!chunkMap.containsKey(coodinateHashCode)) {
            return null;
        }
        return chunkMap.get(coodinateHashCode);
    }

    public void setChunk(Chunk chunk) {
        if (!(chunk instanceof JungleChunk)) {
            throw new IllegalOperationException("Chunk is not of type " + JungleChunk.class.getSimpleName() + ". What on Earth are you doing?");
        }
        JungleChunk jungleChunk = (JungleChunk) chunk;
        chunkMap.put(jungleChunk.getMapKey(), jungleChunk);
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
