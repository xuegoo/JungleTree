package org.jungletree.rainforest.storage;

import org.redisson.api.RedissonClient;

public interface StorageService {

    RedissonClient getStorage();
}
