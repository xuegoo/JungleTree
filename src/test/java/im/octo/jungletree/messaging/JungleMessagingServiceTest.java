package im.octo.jungletree.messaging;

import im.octo.jungletree.rainforest.messaging.Message;
import im.octo.jungletree.rainforest.messaging.MessageHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JungleMessagingServiceTest {

    private JungleMessagingService subject;

    @Before
    public void setUp() throws Exception {
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

    public class TestMessage implements Message {}

    public class TestMessageHandler implements MessageHandler<TestMessage> {

        @Override
        public void handle(TestMessage message) {}
    }
}
