package org.jungletree.storage.storage;

import org.jungletree.rainforest.storage.StorageService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

import javax.inject.Singleton;

@Singleton
public class JungleStorageService implements StorageService {

    private final RedissonClient client;

    public JungleStorageService() {
        this.client = Redisson.create();
    }

    @Override
    public RedissonClient getStorage() {
        return client;
    }
}
