module auth.main {
    requires slf4j.api;
    requires rainforest.messaging;
    requires rainforest.auth;
    requires nimbus.jose.jwt;
    requires rainforest.util;

    uses org.jungletree.rainforest.messaging.MessagingService;
}
