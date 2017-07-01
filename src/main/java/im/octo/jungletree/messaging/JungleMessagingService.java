package im.octo.jungletree.messaging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import im.octo.jungletree.rainforest.messaging.Message;
import im.octo.jungletree.rainforest.messaging.MessageHandler;
import im.octo.jungletree.rainforest.messaging.MessagingService;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.inject.Singleton;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class JungleMessagingService implements MessagingService {

    private static final String BROKER_URL = System.getenv("MESSAGE_BROKER_URL");
    private static final long TIMEOUT = Long.parseLong(System.getenv("MESSAGE_TIMEOUT"));

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> HANDLERS = new ConcurrentHashMap<>();

    private Connection connection;
    private Session session;

    public JungleMessagingService() {
        initJms();
    }

    public Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> getHandlers() {
        return HANDLERS;
    }

    @Override
    public CompletableFuture<Message> sendMessage(Message message) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // TODO: Cache connections
                Destination producerDestination = session.createQueue(message.getClass().getSimpleName() + "Request");
                Destination consumerDestination = session.createQueue(message.getClass().getSimpleName() + "Response");
                MessageProducer producer = session.createProducer(producerDestination);
                MessageConsumer consumer = session.createConsumer(consumerDestination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                TextMessage textMessage = session.createTextMessage(GSON.toJson(message));
                producer.send(textMessage);
                producer.close();

                TextMessage resultMessage = (TextMessage) consumer.receive(TIMEOUT);
                consumer.close();
                return GSON.fromJson(resultMessage.getText(), message.getClass());
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public <M extends Message> void registerMessage(Class<M> messageClass) {
        HANDLERS.put(messageClass, new ArrayList<>());
    }

    @Override
    public <M extends Message> void unregisterMessage(Class<M> messageClass) {
        HANDLERS.remove(messageClass);
    }

    @Override
    public <M extends Message> void registerHandler(Class<M> messageClass, MessageHandler<M> handler) {
        HANDLERS.get(messageClass).add(handler);
    }

    @Override
    public <M extends Message> void unregisterHandler(Class<M> messageClass, MessageHandler<M> handler) {
        HANDLERS.get(messageClass).remove(handler);
    }

    public void shutdown() {
        try {
            session.close();
            connection.close();
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }

    private void initJms() {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
            this.connection = factory.createConnection();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
