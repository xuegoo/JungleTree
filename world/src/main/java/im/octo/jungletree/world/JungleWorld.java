package im.octo.jungletree.world;

import com.google.gson.annotations.Expose;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.world.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "td_component")
public class JungleWorld implements World {

    @Id
    @Expose
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    private UUID uuid;

    @Expose
    @Column
    private String name;

    @Expose
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Dimension dimension;

    private transient final Set<Player> players = Collections.synchronizedSet(new HashSet<>());

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Dimension getDimension() {
        return Dimension.OVERWORLD;
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
