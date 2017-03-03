package im.octo.jungletree.api.world;

public enum Dimension {

    NETHER(-1),
    OVERWORLD(0),
    THE_END(1);

    private final int id;

    Dimension(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
