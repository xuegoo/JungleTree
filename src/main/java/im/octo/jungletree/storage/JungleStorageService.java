package im.octo.jungletree.storage;

import im.octo.jungletree.messaging.message.ContainerMessage;
import im.octo.jungletree.rainforest.messaging.MessagingService;
import im.octo.jungletree.rainforest.storage.Container;
import im.octo.jungletree.rainforest.storage.StorageService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;

@Singleton
public class JungleStorageService implements StorageService {

    @Inject
    private MessagingService messagingService;

    @Override
    public CompletableFuture<Container> open(String container) {
        ContainerMessage message = new ContainerMessage();
        message.setName(container);
        message.setRequest(ContainerMessage.Request.OPEN);

        return messagingService
                .sendMessage(message)
                .thenApplyAsync(m -> ((ContainerMessage) m).getContainer());
    }

    @Override
    public void close(Container container) {
        ContainerMessage message = new ContainerMessage();
        message.setName(container.getName());
        message.setRequest(ContainerMessage.Request.CLOSE);

        messagingService.sendMessage(message);
    }
}
