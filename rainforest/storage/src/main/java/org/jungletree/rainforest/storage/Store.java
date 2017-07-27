package org.jungletree.rainforest.storage;

public interface Store<K, V> {

    boolean isEmpty();

    void clear();

    V get(K key);

    V put(K key, V value);

    boolean containsKey(K key);
}
