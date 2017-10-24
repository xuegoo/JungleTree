module auth.main {
    requires slf4j.api;
    requires rainforest.messaging;
    requires rainforest.auth;
    requires nimbus.jose.jwt;
    requires rainforest.util;
    requires gson;
    requires bcprov.jdk15on;

    uses org.jungletree.rainforest.messaging.MessagingService;

    exports org.jungletree.auth;
    opens org.jungletree.auth;
}
