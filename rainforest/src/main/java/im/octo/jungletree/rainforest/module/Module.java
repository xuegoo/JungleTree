package im.octo.jungletree.rainforest.module;

import im.octo.jungletree.rainforest.messaging.MessageHandler;

import java.util.Collection;

public interface Module {

    String getName();

    String getVersion();

    Collection<MessageHandler> getMessageHandlers();

    void onLoad();

    void onUnload();
}
