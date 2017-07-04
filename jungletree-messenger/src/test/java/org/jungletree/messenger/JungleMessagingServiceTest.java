package org.jungletree.messenger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jungletree.rainforest.messaging.Message;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.jms.*;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ JungleMessagingService.class, GsonBuilder.class, Gson.class, ActiveMQConnectionFactory.class })
public class JungleMessagingServiceTest {

    @Mock
    private GsonBuilder gsonBuilder;

    @Mock
    private Gson gson;

    @Mock
    private ActiveMQConnectionFactory factory;

    @Mock
    private Connection connection;

    @Mock
    private Session session;

    @Mock
    private Queue request;

    @Mock
    private Queue response;

    @Mock
    private MessageProducer producer;

    @Mock
    private MessageConsumer consumer;

    @Mock
    private TextMessage textMessage;

    private JungleMessagingService subject;

    @Before
    public void setUp() throws Exception {
        whenNew(GsonBuilder.class).withAnyArguments().thenReturn(gsonBuilder);
        when(gsonBuilder.setPrettyPrinting()).thenReturn(gsonBuilder);
        doReturn(gson).when(gsonBuilder).create();

        whenNew(ActiveMQConnectionFactory.class).withAnyArguments().thenReturn(factory);
        when(factory.createConnection()).thenReturn(connection);
        when(connection.createSession(Matchers.anyBoolean(), Matchers.anyInt())).thenReturn(session);
        when(session.createQueue(TestMessage.class.getSimpleName() + "Request")).thenReturn(request);
        when(session.createQueue(TestMessage.class.getSimpleName() + "Response")).thenReturn(response);
        when(session.createProducer(request)).thenReturn(producer);
        when(session.createConsumer(response)).thenReturn(consumer);

        this.subject = new JungleMessagingService();
    }

    @After
    public void tearDown() throws Exception {
        this.subject = null;
    }

    @Test
    public void getHandlers() throws Exception {
        // Given
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> handlers;

        // When
        handlers = subject.getHandlers();

        // Then
        assertNotNull(handlers);
    }

    @Test
    public void sendMessage() throws Exception {
        // Given
        when(session.createTextMessage(Matchers.anyString())).thenReturn(textMessage);
        subject.initJms();

        TestMessage message = new TestMessage();

        // When
        subject.sendMessage(message);
    }

    @Test
    public void registerMessage() throws Exception {
        // Given
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> globalHandlers;
        Collection<MessageHandler<? extends Message>> handlers;

        // When
        subject.registerMessage(TestMessage.class);

        // Then
        globalHandlers = subject.getHandlers();
        assertTrue(globalHandlers.containsKey(TestMessage.class));
        handlers = globalHandlers.get(TestMessage.class);

        assertNotNull(handlers);
        assertTrue(handlers.isEmpty());
    }

    @Test
    public void unregisterMessage() throws Exception {
        // Given
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> globalHandlers;

        subject.registerMessage(TestMessage.class);

        // When
        subject.unregisterMessage(TestMessage.class);

        // Then
        globalHandlers = subject.getHandlers();

        assertFalse(globalHandlers.containsKey(TestMessage.class));
    }

    @Test
    public void registerHandler() throws Exception {
        // Given
        subject.registerMessage(TestMessage.class);
        TestMessageHandler handler = new TestMessageHandler();

        // When
        subject.registerHandler(TestMessage.class, handler);

        // Then
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> globalHandlers = subject.getHandlers();
        assertTrue(globalHandlers.containsKey(TestMessage.class));
        Collection<MessageHandler<? extends Message>> handlers = globalHandlers.get(TestMessage.class);

        assertFalse(handlers.isEmpty());
        assertTrue(handlers.contains(handler));
    }

    @Test
    public void unregisterHandler() throws Exception {
        // Given
        subject.registerMessage(TestMessage.class);
        TestMessageHandler handler = new TestMessageHandler();
        subject.registerHandler(TestMessage.class, handler);

        // When
        subject.unregisterHandler(TestMessage.class, handler);

        // Then
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> globalHandlers = subject.getHandlers();
        assertTrue(globalHandlers.containsKey(TestMessage.class));
        Collection<MessageHandler<? extends Message>> handlers = globalHandlers.get(TestMessage.class);

        assertTrue(handlers.isEmpty());
        assertFalse(handlers.contains(handler));
    }

    @Test
    public void shutdown() throws Exception {
        // Given
        subject.initJms();

        // When
        subject.shutdown();

        // Then
        Mockito.verify(session).close();
        Mockito.verify(connection).close();
    }

    @Test(expected = RuntimeException.class)
    public void shutdownHandleException() throws Exception {
        // Given
        doThrow(new JMSException("")).when(session).close();

        subject.initJms();

        // When
        subject.shutdown();

        // Then expects exception
    }

    public class TestMessage implements Message {}

    public class TestMessageHandler implements MessageHandler<TestMessage> {

        @Override
        public void handle(TestMessage message) {}
    }
}
