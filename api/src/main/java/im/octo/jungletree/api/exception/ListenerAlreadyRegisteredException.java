package im.octo.jungletree.api.exception;

public class ListenerAlreadyRegisteredException extends RuntimeException {

    public ListenerAlreadyRegisteredException() {
    }

    public ListenerAlreadyRegisteredException(String message) {
        super(message);
    }

    public ListenerAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ListenerAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }

    public ListenerAlreadyRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
