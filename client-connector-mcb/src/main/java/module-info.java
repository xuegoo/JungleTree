import org.jungletree.clientconnector.mcb.BedrockServer;

module jungletree.clientconnector.mcb {
    requires rainforest.scheduler;

    requires jungletree.jraknet;

    opens org.jungletree.clientconnector.mcb.message.handshake to gson;

    requires slf4j.api;
    requires netty.all;
    requires gson;
    requires java.sql;
    requires rainforest.messaging;
    requires rainforest.auth;
    requires rainforest.util;

    exports org.jungletree.clientconnector.mcb;
    exports org.jungletree.clientconnector.mcb.message;
    exports org.jungletree.clientconnector.mcb.codec;
    exports org.jungletree.clientconnector.mcb.crypto;
    exports org.jungletree.clientconnector.mcb.handler;
    exports org.jungletree.clientconnector.mcb.message.handshake;
    exports org.jungletree.clientconnector.mcb.handler.handshake;

    provides BedrockServer with BedrockServer;
    uses BedrockServer;

    uses org.jungletree.rainforest.scheduler.SchedulerService;
    uses org.jungletree.rainforest.messaging.MessagingService;
}
