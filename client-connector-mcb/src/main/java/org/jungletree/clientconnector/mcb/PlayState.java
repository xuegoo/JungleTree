package org.jungletree.clientconnector.mcb;

public enum PlayState {

    LOGIN_SUCCESS(0),
    LOGIN_FAILED_CLIENT(1),
    LOGIN_FAILED_SERVER(2),
    SPAWN(3);

    private final int ordinal;

    PlayState(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public static PlayState fromOrdinal(int ordinal) {
        for (PlayState state : values()) {
            if (state.ordinal == ordinal) {
                return state;
            }
        }
        return null;
    }
}
