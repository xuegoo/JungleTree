package im.octo.jungletree.world;

import im.octo.jungletree.api.world.block.Block;
import im.octo.jungletree.api.world.Chunk;
import im.octo.jungletree.api.world.World;
import im.octo.jungletree.api.world.block.Material;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "blocks", indexes = {
        @Index(name = "idx_uuid_coords", columnList = "uuid,x,z"),
})
public class JungleBlock implements Block {

    static final String HASH_CODE_PREFIX = "JTB|";
    static final String HASH_CODE_SEPARATOR = ",";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private int mapKey;

    @Column
    private int x;

    @Column
    private int y;

    @Column
    private int z;

    @Enumerated
    @Column
    private Material material;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private JungleChunk chunk;

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
        this.mapKey = Objects.hash(HASH_CODE_PREFIX, chunk.getMapKey(), HASH_CODE_SEPARATOR, getX(), HASH_CODE_SEPARATOR, getZ(), HASH_CODE_SEPARATOR, getY());
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(JungleChunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public World getWorld() {
        return getChunk().getWorld();
    }
}
