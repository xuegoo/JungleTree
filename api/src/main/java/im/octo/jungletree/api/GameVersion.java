package im.octo.jungletree.api;

public enum GameVersion {

    MC_1_11_2("1.11.2", 316),
    MC_1_11_1("1.11.1", 316),
    MC_1_11("1.11", 315);

    private final String name;
    private final int protocolVersion;

    GameVersion(String name, int protocolVersion) {
        this.name = name;
        this.protocolVersion = protocolVersion;
    }

    public String getName() {
        return name;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }
}
