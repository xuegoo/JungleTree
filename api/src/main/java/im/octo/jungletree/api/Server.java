package im.octo.jungletree.api;

public interface Server {

    default String getApiName() {
        return Server.class.getPackage().getSpecificationTitle();
    }

    default String getApiVersion() {
        return Server.class.getPackage().getSpecificationVersion();
    }

    String getImplementationName();

    String getImplementationVersion();
}
