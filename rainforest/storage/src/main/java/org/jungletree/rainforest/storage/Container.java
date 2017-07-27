package org.jungletree.rainforest.storage;

import java.util.Map;

public interface Container {

    String getName();

    boolean isEmpty();

    void clear();

    <K, V> Map<K, V> get(String name);

    <K, V> void save(String name, Map<K, V> store);
}
