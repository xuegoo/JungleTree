package org.jungletree.rainforest.connector.exception;

public final class UnknownPacketException extends RuntimeException {

    private final int opCode;
    private final int length;

    public UnknownPacketException(String message, int opCode, int length) {
        super(message + ": opCode=" + opCode + ", length=" + length);
        this.opCode = opCode;
        this.length = length;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getLength() {
        return length;
    }
}
