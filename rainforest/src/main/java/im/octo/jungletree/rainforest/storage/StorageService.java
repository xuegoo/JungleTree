package im.octo.jungletree.rainforest.storage;

public interface StorageService {

    Container open(String container);

    void close(Container container);
}
