package im.octo.jungletree.api.exception;

public class WorldGeneratorNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Generator by the name \"%s\" not found";

    public WorldGeneratorNotFoundException() {
    }

    public WorldGeneratorNotFoundException(String name) {
        super(String.format(MESSAGE, name));
    }

    public WorldGeneratorNotFoundException(String name, Throwable cause) {
        super(String.format(MESSAGE, name), cause);
    }

    public WorldGeneratorNotFoundException(Throwable cause) {
        super(cause);
    }

    public WorldGeneratorNotFoundException(String name, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(String.format(MESSAGE, name), cause, enableSuppression, writableStackTrace);
    }
}
