package org.jungletree.messenger;

import org.jungletree.rainforest.messaging.Message;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.jms.*;
import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JungleMessagingServiceTest {

    private JungleMessagingService subject;

    @Mock
    private Session session;

    @Mock
    private MessageConsumer consumer;

    @Mock
    private MessageProducer producer;

    @Mock
    private Queue queue;

    @BeforeEach
    void setUp() throws Exception {
        this.subject = new JungleMessagingService();
        subject.setSession(session);

        when(session.createQueue(anyString())).thenReturn(queue);
        when(session.createConsumer(Mockito.any(Destination.class))).thenReturn(consumer);
        when(session.createProducer(Mockito.any(Destination.class))).thenReturn(producer);
    }

    @AfterEach
    void tearDown() {
        this.subject.shutdown();
        this.subject = null;
    }

    @Test
    void getHandlers() {
        // Given
        subject.registerMessage(DummyMessage.class);
        subject.registerHandler(DummyMessage.class, dummyMessage -> {});

        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> result;

        // When
        result = subject.getHandlers();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void registerMessage() {
        // Given
        Map<String, Queue> requestQueues;
        Map<String, Queue> responseQueues;

        // When
        subject.registerMessage(DummyMessage.class);

        // Then
        requestQueues = subject.getRequestQueues();
        responseQueues = subject.getResponseQueues();

        assertNotNull(requestQueues);
        assertFalse(requestQueues.isEmpty());
        assertTrue(requestQueues.containsKey("DummyMessageRequest"));
        assertNotNull(requestQueues.get("DummyMessageRequest"));

        assertNotNull(responseQueues);
        assertFalse(responseQueues.isEmpty());
        assertTrue(responseQueues.containsKey("DummyMessageResponse"));
        assertNotNull(responseQueues.get("DummyMessageResponse"));
    }

    @Test
    void unregisterMessage() {
        // Given
        Map<String, Queue> requestQueues;
        Map<String, Queue> responseQueues;
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> handlers;

        subject.registerMessage(DummyMessage.class);
        subject.registerHandler(DummyMessage.class, message -> {});

        // When
        subject.unregisterMessage(DummyMessage.class);

        // Then
        requestQueues = subject.getRequestQueues();
        responseQueues = subject.getResponseQueues();
        handlers = subject.getHandlers();

        assertTrue(requestQueues.isEmpty());
        assertTrue(responseQueues.isEmpty());
        assertTrue(handlers.isEmpty());
    }

    @Test
    void registerHandler() {
        // Given
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> handlers;
        MessageHandler<DummyMessage> handler = message -> {};

        subject.registerMessage(DummyMessage.class);

        // When
        subject.registerHandler(DummyMessage.class, handler);

        // Then
        handlers = subject.getHandlers();
        assertFalse(handlers.isEmpty());
    }

    @Test
    void unregisterHandler() {
        // Given
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> handlers;
        MessageHandler<DummyMessage> handler = message -> {};
        MessageHandler<DummyMessage> handler2 = message -> {};

        subject.registerMessage(DummyMessage.class);
        subject.registerHandler(DummyMessage.class, handler);
        subject.registerHandler(DummyMessage.class, handler2);

        // When
        subject.unregisterHandler(DummyMessage.class, handler);

        // Then
        handlers = subject.getHandlers();
        assertFalse(handlers.isEmpty());
        assertFalse(handlers.get(DummyMessage.class).contains(handler));
        assertTrue(handlers.get(DummyMessage.class).contains(handler2));
    }

    @Test
    void unregisterHandlerRemovesBaseMessageIfNoOtherHandlersPresent() {
        // Given
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> handlers;
        MessageHandler<DummyMessage> handler = message -> {};

        subject.registerMessage(DummyMessage.class);
        subject.registerHandler(DummyMessage.class, handler);

        // When
        subject.unregisterHandler(DummyMessage.class, handler);

        // Then
        handlers = subject.getHandlers();
        assertTrue(handlers.isEmpty());
    }
}
