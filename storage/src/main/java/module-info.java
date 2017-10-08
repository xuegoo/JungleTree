module jungletree.storage {
    requires rainforest.storage;
    requires redisson;
    exports org.jungletree.storage.storage;

    provides org.jungletree.rainforest.storage.StorageService with org.jungletree.storage.storage.JungleStorageService;
}
