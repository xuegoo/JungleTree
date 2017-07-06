package org.jungletree.service.storage;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.service.storage.handlers.ContainerMessageHandler;
import org.jungletree.storage.storage.ContainerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class StorageServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceApplication.class);

    private final Injector injector;

    @Inject
    private MessagingService messagingService;

    private StorageServiceApplication() {
        long startTime = System.currentTimeMillis();

        log.info("Starting " + StorageServiceApplication.class.getSimpleName());

        log.trace("Injecting dependencies");
        this.injector = Guice.createInjector(new StorageServiceGuiceModule());
        injector.injectMembers(this);

        log.trace("Registering messages");
        messagingService.registerMessage(ContainerMessage.class);

        log.trace("Registering message handlers");
        messagingService.registerHandler(ContainerMessage.class, new ContainerMessageHandler());

        log.info(String.format("Done <%dms>!", Math.round(System.currentTimeMillis() - startTime)));
    }

    public static void main(String[] args) {
        new StorageServiceApplication();
    }
}
