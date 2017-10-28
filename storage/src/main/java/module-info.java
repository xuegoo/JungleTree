import org.jungletree.storage.storage.JungleStorageFactory;

module jungletree.storage {
    requires rainforest.storage;
    requires redisson;
    exports org.jungletree.storage.storage;

    provides org.redisson.api.RedissonClient with JungleStorageFactory;
}
