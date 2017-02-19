package im.octo.jungletree.api.event;

public interface Event {

    String getName();

    default Type getType() {
        return Type.GENERIC;
    }

    enum Type {
        GENERIC,
        SERVER,
        NETWORK,
        WORLD,
        ENTITY,
        PLAYER,
        COMMAND
    }
}
