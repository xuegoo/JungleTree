package im.octo.jungletree.api.exception;

public class InvalidPacketException extends RuntimeException {

    public InvalidPacketException() {
    }

    public InvalidPacketException(String message) {
        super(message);
    }

    public InvalidPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPacketException(Throwable cause) {
        super(cause);
    }

    public InvalidPacketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
