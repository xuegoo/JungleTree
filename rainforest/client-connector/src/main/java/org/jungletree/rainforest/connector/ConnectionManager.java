package org.jungletree.rainforest.connector;

import io.netty.channel.Channel;

public interface ConnectionManager {

    Session newSession(Channel c);

    void sessionInactivated(Session session);

    void shutdown();
}
