package org.jungletree.rainforest.storage;

import java.util.concurrent.CompletableFuture;

public interface StorageService {

    CompletableFuture<Container> open(String container);

    void close(Container container);
}
