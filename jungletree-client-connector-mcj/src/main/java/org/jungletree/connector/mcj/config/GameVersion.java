package org.jungletree.connector.mcj.config;

public enum GameVersion {

    MC_1_12_2("1.12.2", 339),
    MC_1_12_1("1.12.1", 338),
    MC_1_12("1.12", 335);

    private final String versionName;
    private final int protocolVersion;

    GameVersion(String versionName, int protocolVersion) {
        this.versionName = versionName;
        this.protocolVersion = protocolVersion;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }
}
