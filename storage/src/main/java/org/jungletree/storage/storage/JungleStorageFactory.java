package org.jungletree.storage.storage;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public final class JungleStorageFactory {

    private static final String REDIS_HOST = System.getenv("REDIS_HOST") != null ? System.getenv("REDIS_HOST") : "localhost";

    private static RedissonClient client;

    private JungleStorageFactory() {}

    public static RedissonClient provider() {
        if (client == null) {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://" + REDIS_HOST + ":6379");
            client = Redisson.create(config);
        }
        return client;
    }
}
