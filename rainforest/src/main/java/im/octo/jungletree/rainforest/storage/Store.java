package im.octo.jungletree.rainforest.storage;

public interface Store<K, V> {

    boolean isEmpty();

    void clear();

    V get(K key);

    void put(K key, V value);

    boolean containsKey(K key);
}
