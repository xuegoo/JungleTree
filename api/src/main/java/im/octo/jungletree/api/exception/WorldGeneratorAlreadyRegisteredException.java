package im.octo.jungletree.api.exception;

public class WorldGeneratorAlreadyRegisteredException extends RuntimeException {

    private static final String MESSAGE = "Generator by the name \"%s\" already registered";

    public WorldGeneratorAlreadyRegisteredException() {
    }

    public WorldGeneratorAlreadyRegisteredException(String name) {
        super(String.format(MESSAGE, name));
    }

    public WorldGeneratorAlreadyRegisteredException(String name, Throwable cause) {
        super(String.format(MESSAGE, name), cause);
    }

    public WorldGeneratorAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }

    public WorldGeneratorAlreadyRegisteredException(String name, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(String.format(MESSAGE, name), cause, enableSuppression, writableStackTrace);
    }
}
