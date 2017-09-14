package org.jungletree.connector.mcj.protocol;

public enum HandshakeState {

    STATUS(1),
    LOGIN(2);

    private final int id;

    HandshakeState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
