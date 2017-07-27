package org.jungletree.messenger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jungletree.rainforest.messaging.Message;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;

import javax.inject.Singleton;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class JungleMessagingService implements MessagingService {

    private static final String BROKER_URL = System.getenv("MESSAGE_BROKER_URL") != null ? System.getenv("MESSAGE_BROKER_URL") : "vm://localhost";
    private static final long TIMEOUT = Long.parseLong(System.getenv("MESSAGE_TIMEOUT") != null ? System.getenv("MESSAGE_TIMEOUT") : "1000");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Connection connection;
    private Session session;

    private final Map<String, Queue> requestQueues = new ConcurrentHashMap<>();
    private final Map<String, Queue> responseQueues = new ConcurrentHashMap<>();
    private final Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> handlers = new ConcurrentHashMap<>();

    public Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> getHandlers() {
        return handlers;
    }

    @Override
    public void start() {
        initJms();
    }

    @Override
    public void shutdownGracefully() {
        shutdown();
    }

    @Override
    public void shutdownForcefully() {

    }

    @Override
    public CompletableFuture<Message> sendMessage(Message message) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // TODO: Cache connections
                Destination producerDestination = requestQueues.get(message.getClass().getSimpleName() + "Request");
                Destination consumerDestination = responseQueues.get(message.getClass().getSimpleName() + "Response");
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
        String requestQueueName = messageClass.getSimpleName() + "Request";
        String responseQueueName = messageClass.getSimpleName() + "Response";

        try {
            if (!requestQueues.containsKey(requestQueueName)) {
                Queue requestQueue = session.createQueue(requestQueueName);
                this.requestQueues.put(requestQueueName, requestQueue);
            }

            if (!responseQueues.containsKey(responseQueueName)) {
                Queue responseQueue = session.createQueue(requestQueueName);
                this.requestQueues.put(requestQueueName, responseQueue);
            }
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
        handlers.put(messageClass, new ArrayList<>());
    }

    @Override
    public <M extends Message> void unregisterMessage(Class<M> messageClass) {
        handlers.remove(messageClass);
    }

    @Override
    public <M extends Message> void registerHandler(Class<M> messageClass, MessageHandler<M> handler) {
        Queue queue = requestQueues.get(messageClass.getSimpleName() + "Request");
        try {
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        M result = GSON.fromJson(textMessage.getText(), messageClass);
                        handler.handle(result);
                    } catch (JMSException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
        handlers.get(messageClass).add(handler);
    }

    @Override
    public <M extends Message> void unregisterHandler(Class<M> messageClass, MessageHandler<M> handler) {
        handlers.get(messageClass).remove(handler);
    }

    void shutdown() {
        try {
            session.close();
            connection.close();
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }

    void initJms() {
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
            this.connection = factory.createConnection();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }
}
