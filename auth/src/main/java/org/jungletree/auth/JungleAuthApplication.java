package org.jungletree.auth;

import org.jungletree.rainforest.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.messages.JwtAuthRequestMessage;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class JungleAuthApplication implements MessageHandler<JwtAuthRequestMessage> {

    private static final Logger log = LoggerFactory.getLogger(JungleAuthApplication.class);

    private final MessagingService messaging;

    private JungleAuthApplication() {
        this.messaging = ServiceLoader.load(MessagingService.class).findFirst().orElseThrow(NoSuchElementException::new);

        registerMessageHandlers();

        log.info("Started! Listening for incoming token validation requests.");
    }

    private void registerMessageHandlers() {
        log.trace("Registering message handlers");
        messaging.start();
        messaging.registerMessage(JwtAuthRequestMessage.class);
        messaging.registerHandler(JwtAuthRequestMessage.class, this);
        messaging.registerMessage(JwtAuthReponseMessage.class);
    }

    @Override
    public void handle(JwtAuthRequestMessage message) {
        log.trace("Inbound JWT validation request: {}", message.toString());
    }
}
