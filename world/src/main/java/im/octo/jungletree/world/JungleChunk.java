package im.octo.jungletree.world;

import im.octo.jungletree.api.exception.IllegalOperationException;
import im.octo.jungletree.api.world.block.Block;
import im.octo.jungletree.api.world.Chunk;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static im.octo.jungletree.world.JungleBlock.HASH_CODE_PREFIX;
import static im.octo.jungletree.world.JungleBlock.HASH_CODE_SEPARATOR;

@Entity
@Table(name = "chunks", indexes = {
        @Index(name = "idx_uuid_coords", columnList = "uuid,x,z"),
})
public class JungleChunk implements Chunk {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private int mapKey;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private JungleWorld world;

    @Column
    private int x;

    @Column
    private int z;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Map<Integer, JungleBlock> blockMap = new ConcurrentHashMap<>();

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getMapKey() {
        return mapKey;
    }

    public void setMapKey(int blockMapKey) {
        this.mapKey = blockMapKey;
    }

    public void updateMapKey() {
        this.mapKey = Objects.hash(HASH_CODE_PREFIX, getX(), HASH_CODE_SEPARATOR, getZ());
    }

    @Override
    public JungleWorld getWorld() {
        return world;
    }

    public void setWorld(JungleWorld world) {
        this.world = world;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public Block getBlock(int blockChunkX, int blockChunkY, int blockChunkZ) {
        int coodinateHashCode = Objects.hash(HASH_CODE_PREFIX, HASH_CODE_SEPARATOR, blockChunkX, HASH_CODE_SEPARATOR, blockChunkY, HASH_CODE_SEPARATOR, blockChunkZ);
        if (!blockMap.containsKey(coodinateHashCode)) {
            return null;
        }
        return blockMap.get(coodinateHashCode);
    }

    public void setBlock(Block block) {
        if (!(block instanceof JungleBlock)) {
            throw new IllegalOperationException("Block is not of type " + JungleBlock.class.getSimpleName() + ". What on Earth are you doing?");
        }
        JungleBlock jungleBlock = (JungleBlock) block;
        blockMap.put(jungleBlock.getMapKey(), jungleBlock);
    }
}
