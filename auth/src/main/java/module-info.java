module auth.main {
    requires slf4j.api;
    requires rainforest.messaging;
    requires rainforest.auth;
    requires nimbus.jose.jwt;

    uses org.jungletree.rainforest.messaging.MessagingService;
}
