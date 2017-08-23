package org.jungletree.connector.mcj;

import com.google.inject.Guice;
import com.google.inject.Inject;
import org.jungletree.rainforest.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JungleMcjApplication {

    public static final String MESSENGER_NAME = "CONNECTOR_MCJ";

    private static final Logger log = LoggerFactory.getLogger(JungleMcjApplication.class);

    @Inject
    private MessagingService messaging;

    private JungleMcjApplication() {
        long start = System.currentTimeMillis();
        log.info("Starting JungleTree Minecraft Java Edition connector");

        log.trace("Injecting dependencies");
        Guice.createInjector(new JungleMcjGuiceModule()).injectMembers(this);

        log.trace("Starting messaging service");
        messaging.start();

        log.info("Done {}ms", (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        new JungleMcjApplication();
    }
}
