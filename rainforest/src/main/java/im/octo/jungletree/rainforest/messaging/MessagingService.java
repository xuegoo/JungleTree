package im.octo.jungletree.rainforest.messaging;

public interface MessagingService {

    <M extends Message> void registerMessage(Class<M> messageClass);

    <M extends Message> void unregisterMessage(Class<M> messageClass);

    <M extends Message> void registerHandler(MessageHandler<M> handler);

    <M extends Message> void unregisterHandler(MessageHandler<M> handler);
}
