package im.octo.jungletree.rainforest.storage;

public interface Container {

    boolean isEmpty();

    void clear();

    <K, V> void get(Store<K, V> store);

    <K, V> void save(Store<K, V> store);
}
