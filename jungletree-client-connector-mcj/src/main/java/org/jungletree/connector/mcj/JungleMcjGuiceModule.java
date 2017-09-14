package org.jungletree.connector.mcj;

import com.flowpowered.network.ConnectionManager;
import com.google.inject.AbstractModule;
import org.jungletree.connector.mcj.config.JClientConnectorResourceService;
import org.jungletree.messenger.JungleMessagingService;
import org.jungletree.network.ClientConnectorResourceService;
import org.jungletree.rainforest.connector.ClientConnector;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.scheduler.SchedulerService;
import org.jungletree.scheduler.JungleSchedulerService;

import java.util.concurrent.CountDownLatch;

public class JungleMcjGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessagingService.class).to(JungleMessagingService.class);
        bind(SchedulerService.class).to(JungleSchedulerService.class);

        bind(ConnectionManager.class).to(JungleClientConnectorMcj.class);
        bind(ClientConnector.class).to(JungleClientConnectorMcj.class);

        bind(ClientConnectorResourceService.class).to(JClientConnectorResourceService.class);
        bind(CountDownLatch.class).toInstance(new CountDownLatch(3));
    }
}
