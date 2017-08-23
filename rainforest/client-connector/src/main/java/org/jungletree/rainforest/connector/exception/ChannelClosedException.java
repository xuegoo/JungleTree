package org.jungletree.rainforest.connector.exception;

public final class ChannelClosedException extends RuntimeException {

    public ChannelClosedException() {
    }

    public ChannelClosedException(String message) {
        super(message);
    }
}
