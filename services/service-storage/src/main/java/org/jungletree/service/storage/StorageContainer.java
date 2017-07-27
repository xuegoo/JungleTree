package org.jungletree.service.storage;

import org.jungletree.rainforest.storage.Container;
import org.redisson.api.RedissonClient;

import java.util.Map;

public class StorageContainer implements Container {

    private String name;
    private RedissonClient client;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClient(RedissonClient client) {
        this.client = client;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public <K, V> Map<K, V> get(String name) {
        return client.getMap(name);
    }

    @Override
    public <K, V> void save(String name, Map<K, V> store) {
    }
}
