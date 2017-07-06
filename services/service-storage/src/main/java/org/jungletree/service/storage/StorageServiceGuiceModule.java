package org.jungletree.service.storage;

import com.google.inject.AbstractModule;
import org.jungletree.messenger.JungleMessagingService;
import org.jungletree.rainforest.messaging.MessagingService;

public class StorageServiceGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessagingService.class).to(JungleMessagingService.class);
    }
}
