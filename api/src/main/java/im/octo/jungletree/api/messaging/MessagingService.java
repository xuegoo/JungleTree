package im.octo.jungletree.api.messaging;

import javax.jms.Queue;

public interface MessagingService {

    void open(String connectionUrl);

    void close();

    Queue createQueue(String name);
}
