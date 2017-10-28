import org.jungletree.messenger.JungleMessenger;

module jungletree.messenger {
    requires rainforest.messaging;

    exports org.jungletree.messenger;

    requires java.naming;
    requires gson;
    requires rabbitmq.jms;
    requires jms;
    requires java.sql;

    provides org.jungletree.rainforest.messaging.Messenger with JungleMessenger;
}
