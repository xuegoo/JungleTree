package im.octo.jungletree.rainforest.messaging;

import java.util.concurrent.CompletableFuture;

public interface MessagingService {

    CompletableFuture<Message> sendMessage(Message message);

    <M extends Message> void registerMessage(Class<M> messageClass);

    <M extends Message> void unregisterMessage(Class<M> messageClass);

    <M extends Message> void registerHandler(Class<M> messageClass, MessageHandler<M> handler);

    <M extends Message> void unregisterHandler(Class<M> messageClass, MessageHandler<M> handler);
}
