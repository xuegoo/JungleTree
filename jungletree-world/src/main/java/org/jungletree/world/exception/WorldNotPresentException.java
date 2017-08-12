package org.jungletree.world.exception;

public class WorldNotPresentException extends RuntimeException {

    public WorldNotPresentException(String world) {
        super(String.format("World \"%s\" does not exist! Create the world first.", world));
    }
}
