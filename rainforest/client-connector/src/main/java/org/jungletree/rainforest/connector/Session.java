package org.jungletree.rainforest.connector;

import org.jungletree.rainforest.connector.exception.ChannelClosedException;

public interface Session {

    <T extends Message> void messageReceived(T message);

    Protocol getProtocol();

    MessageProcessor getProcessor();

    void send(Message message) throws ChannelClosedException;

    void sendAll(Message... messages) throws ChannelClosedException;

    void disconnect();

    void onDisconnect();

    void onReady();

    void onInboundThrowable(Throwable t);
}
