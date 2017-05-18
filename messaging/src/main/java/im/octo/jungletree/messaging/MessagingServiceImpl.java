package im.octo.jungletree.messaging;

import im.octo.jungletree.api.messaging.MessagingService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

public class MessagingServiceImpl implements MessagingService {

    private static final Logger log = LoggerFactory.getLogger(MessagingServiceImpl.class);

    private ActiveMQConnectionFactory factory;
    private Connection connection;
    private Session session;

    @Override
    public void open(String connectionUrl) {
        try {
            factory = new ActiveMQConnectionFactory(connectionUrl);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException ex) {
            log.error("Connection to messaging service failed. URL: \"" + connectionUrl + "\"", ex);
        }
    }

    @Override
    public void close() {
    }

    @Override
    public Queue createQueue(String name) {
        return null;
    }
}
