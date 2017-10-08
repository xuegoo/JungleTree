package org.jungletree.storage.storage;

import org.jungletree.rainforest.storage.StorageService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class JungleStorageService implements StorageService {

    private static final String REDIS_HOST = System.getenv("REDIS_HOST") != null ? System.getenv("REDIS_HOST") : "localhost";

    private final RedissonClient client;

    public JungleStorageService() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + REDIS_HOST + ":6379");
        this.client = Redisson.create(config);
    }

    @Override
    public RedissonClient getStorage() {
        return client;
    }
}
