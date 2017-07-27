package org.jungletree.service.storage.handlers;

import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.service.storage.StorageContainer;
import org.jungletree.storage.storage.ContainerMessage;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;

public class ContainerMessageHandler implements MessageHandler<ContainerMessage> {

    private static final Logger log = LoggerFactory.getLogger(ContainerMessageHandler.class);

    @Inject
    private MessagingService messagingService;

    private final RedissonClient client;

    public ContainerMessageHandler(RedissonClient client) {
        this.client = client;
    }

    @Override
    public void handle(ContainerMessage message) {
        log.info("Got ContainerMessage!");
        if (!message.getRequest().equals(ContainerMessage.Request.OPEN)) {
            return;
        }

        StorageContainer container = new StorageContainer();
        container.setName(message.getName());
        container.setClient(client);
    }
}
