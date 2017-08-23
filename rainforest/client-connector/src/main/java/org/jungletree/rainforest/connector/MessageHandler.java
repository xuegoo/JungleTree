package org.jungletree.rainforest.connector;

public interface MessageHandler<S extends Session, T extends Message> {

    void handle(S session, T message);
}
