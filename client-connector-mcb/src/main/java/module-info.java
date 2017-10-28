module jungletree.clientconnector.mcb {
    requires rainforest.scheduler;

    requires jungletree.jraknet;

    opens org.jungletree.clientconnector.mcb.packet.handshake to gson;

    requires netty.all;
    requires gson;
    requires java.sql;
    requires rainforest.messaging;
    requires rainforest.auth;
    requires rainforest.util;
    requires org.slf4j;
    requires nimbus.jose.jwt;
    requires bcprov.jdk15on;

    exports org.jungletree.clientconnector.mcb;
    exports org.jungletree.clientconnector.mcb.packet;
    exports org.jungletree.clientconnector.mcb.codec;
    exports org.jungletree.clientconnector.mcb.codec.handshake;
    exports org.jungletree.clientconnector.mcb.crypto;
    exports org.jungletree.clientconnector.mcb.handler;
    exports org.jungletree.clientconnector.mcb.packet.handshake;
    exports org.jungletree.clientconnector.mcb.handler.handshake;

    uses org.jungletree.rainforest.scheduler.Scheduler;
    uses org.jungletree.rainforest.messaging.Messenger;
}
