package org.jungletree.connector.mcj;

import com.google.inject.AbstractModule;
import org.jungletree.messenger.JungleMessagingService;
import org.jungletree.rainforest.connector.NetworkServer;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.scheduler.SchedulerService;
import org.jungletree.rainforest.storage.StorageService;
import org.jungletree.scheduler.JungleSchedulerService;
import org.jungletree.storage.storage.JungleStorageService;

public class JungleMcjGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessagingService.class).to(JungleMessagingService.class);
        bind(StorageService.class).to(JungleStorageService.class);
        bind(SchedulerService.class).to(JungleSchedulerService.class);
        bind(NetworkServer.class).to(JungleNetworkServer.class);
    }
}
