package im.octo.jungletree;

import com.google.gson.GsonBuilder;
import im.octo.jungletree.messaging.JungleMessagingService;
import im.octo.jungletree.messaging.message.ContainerMessage;

public class Application {

    public static void main(String[] args) {
        JungleMessagingService messagingService = new JungleMessagingService();
        messagingService.initJms();

        ContainerMessage message = new ContainerMessage();
        message.setName("dummy");
        message.setRequest(ContainerMessage.Request.OPEN);

        messagingService.sendMessage(message).thenAcceptAsync(m -> {
            ContainerMessage result = (ContainerMessage) m;
            String resultJson = new GsonBuilder().setPrettyPrinting().create().toJson(result);
            System.out.println(resultJson);
        });
    }
}
