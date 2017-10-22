package org.jungletree.clientconnector.mcb.handler;

import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.message.Message;

public interface MessageHandler<T extends Message> {

    void handle(ClientConnection client, T message);
}
