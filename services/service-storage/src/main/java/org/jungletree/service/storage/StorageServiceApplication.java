package org.jungletree.service.storage;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.service.storage.handlers.ContainerMessageHandler;
import org.jungletree.storage.storage.ContainerMessage;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class StorageServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceApplication.class);

    private final Injector injector;

    @Inject
    private MessagingService messagingService;

    private final RedissonClient client;

    private StorageServiceApplication() {
        long startTime = System.currentTimeMillis();

        log.info("Starting " + StorageServiceApplication.class.getSimpleName());

        log.trace("Injecting dependencies");
        this.injector = Guice.createInjector(new StorageServiceGuiceModule());
        injector.injectMembers(this);

        messagingService.start();

        log.trace("Registering messages");
        messagingService.registerMessage(ContainerMessage.class);

        log.trace("Initializing Redis");
        this.client = initRedis();

        log.trace("Registering message handlers");
        messagingService.registerHandler(ContainerMessage.class, new ContainerMessageHandler(client));

        log.info(String.format("Done <%dms>!", Math.round(System.currentTimeMillis() - startTime)));
    }

    private RedissonClient initRedis() {
        Config config = new Config()
                .setThreads(1)
                .setNettyThreads(1);

        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setRetryAttempts(0)
                .setSubscriptionConnectionPoolSize(1);

        return Redisson.create(config);
    }

    public static void main(String[] args) {
        new StorageServiceApplication();
    }
}
