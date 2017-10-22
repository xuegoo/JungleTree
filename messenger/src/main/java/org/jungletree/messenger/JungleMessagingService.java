package org.jungletree.messenger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import org.jungletree.rainforest.messaging.Message;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;

import javax.jms.*;
import javax.jms.Queue;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JungleMessagingService implements MessagingService {

    private static final String BROKER_HOST = System.getenv("MESSAGE_BROKER_URL") != null ? System.getenv("MESSAGE_BROKER_URL") : "localhost";
    private static final String BROKER_USERNAME = System.getenv("MESSAGE_BROKER_USERNAME") != null ? System.getenv("MESSAGE_BROKER_USERNAME") : "jungletree";
    private static final String BROKER_PASSWORD = System.getenv("MESSAGE_BROKER_PASSWORD") != null ? System.getenv("MESSAGE_BROKER_PASSWORD") : "changeme";
    private static final long TIMEOUT = Long.parseLong(System.getenv("MESSAGE_TIMEOUT") != null ? System.getenv("MESSAGE_TIMEOUT") : "10000");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Connection connection;
    private Session session;

    private final Map<String, Queue> requestQueues;
    private final Map<String, Queue> responseQueues;
    private final Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> handlers;

    public JungleMessagingService() {
        this.requestQueues = new ConcurrentHashMap<>();
        this.responseQueues = new ConcurrentHashMap<>();
        this.handlers = new ConcurrentHashMap<>();
    }

    public Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> getHandlers() {
        return handlers;
    }

    @Override
    public void listenForJvmShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    @Override
    public void start() {
        initJms();
    }

    @Override
    public void sendMessage(Message message) {
        try {
            // TODO: Cache connections
            Destination producerDestination = requestQueues.get(message.getClass().getSimpleName() + "Request");
            MessageProducer producer = session.createProducer(producerDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            TextMessage textMessage = session.createTextMessage(GSON.toJson(message));
            producer.send(textMessage);
            producer.close();
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
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
                Queue responseQueue = session.createQueue(responseQueueName);
                this.responseQueues.put(responseQueueName, responseQueue);
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

    @SuppressWarnings("unchecked")
    @Override
    public <M extends Message> void registerHandler(Class<M> messageClass, MessageHandler<M> handler) {
        Collection<MessageHandler<? extends Message>> messageHandlers;

        if (handlers.containsKey(messageClass)) {
            messageHandlers = handlers.get(messageClass);
        } else {
            createMessageConsumerForMessage(messageClass);
            messageHandlers = Collections.synchronizedSet(new HashSet<>());
        }
        messageHandlers.add(handler);
        handlers.put(messageClass, messageHandlers);
    }

    @Override
    public <M extends Message> void unregisterHandler(Class<M> messageClass, MessageHandler<M> handler) {
        handlers.get(messageClass).remove(handler);
    }

    @Override
    public void shutdown() {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Visible for testing
    void setSession(Session session) {
        this.session = session;
    }

    private void initJms() {
        try {
            RMQConnectionFactory factory = new RMQConnectionFactory();
            factory.setUsername(BROKER_USERNAME);
            factory.setPassword(BROKER_PASSWORD);
            factory.setHost(BROKER_HOST);
            factory.setVirtualHost("/");
            this.connection = factory.createConnection();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }

    private <M extends Message> void createMessageConsumerForMessage(Class<M> messageClass) {
        Queue queue = requestQueues.get(messageClass.getSimpleName() + "Request");
        try {
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        M result = GSON.fromJson(textMessage.getText(), messageClass);

                        // Send to all handlers of this message
                        handlers.get(messageClass).forEach(h -> ((MessageHandler<M>) h).handle(result));
                    } catch (JMSException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        } catch (JMSException ex) {
            throw new RuntimeException(ex);
        }
    }
}
