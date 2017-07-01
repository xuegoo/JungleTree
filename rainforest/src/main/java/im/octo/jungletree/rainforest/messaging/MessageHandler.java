package im.octo.jungletree.rainforest.messaging;

public interface MessageHandler<M extends Message> {

    void handle(M message);
}
