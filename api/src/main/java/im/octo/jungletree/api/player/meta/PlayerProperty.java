package im.octo.jungletree.api.player.meta;

public class PlayerProperty {

    private final String name;
    private final String value;
    private final String signature;
    private final boolean signed;

    public PlayerProperty(String name, String value) {
        this(name, value, null);
    }

    public PlayerProperty(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
        this.signed = signature != null;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

    public boolean isSigned() {
        return signed;
    }
}
