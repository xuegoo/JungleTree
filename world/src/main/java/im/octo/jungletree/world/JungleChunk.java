package im.octo.jungletree.world;

import im.octo.jungletree.api.world.Chunk;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private JungleWorld world;

    @Column
    private int x;

    @Column
    private int z;

    @Override
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}
