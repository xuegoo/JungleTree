package org.jungletree.auth;

import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthRequestMessage;
import org.jungletree.rainforest.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class JungleAuthApplication {

    private static final Logger log = LoggerFactory.getLogger(JungleAuthApplication.class);

    private final MessagingService messaging;
    private final JwtAuthRequestHandler authHandler;

    private JungleAuthApplication() {
        this.messaging = ServiceLoader.load(MessagingService.class).findFirst().orElseThrow(NoSuchElementException::new);
        messaging.listenForJvmShutdown();

        this.authHandler = new JwtAuthRequestHandler(messaging);

        registerMessageHandlers();
        log.info("Started! Listening for incoming token validation requests.");
    }

    private void registerMessageHandlers() {
        log.info("Registering message handlers");
        messaging.start();
        messaging.registerMessage(JwtAuthRequestMessage.class);
        messaging.registerHandler(JwtAuthRequestMessage.class, authHandler);
        messaging.registerMessage(JwtAuthReponseMessage.class);
    }

    public static void main(String[] args) {
        new JungleAuthApplication();
    }
}
