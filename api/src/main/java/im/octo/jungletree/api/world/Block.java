package im.octo.jungletree.api.world;

public interface Block {

    int getX();

    int getY();

    int getZ();

    World getWorld();

    default Location getLocation() {
        return new Location(getWorld(), getX(), getY(), getZ());
    }
}
