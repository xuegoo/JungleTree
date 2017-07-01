package im.octo.jungletree.messaging;

import im.octo.jungletree.rainforest.messaging.Message;
import im.octo.jungletree.rainforest.messaging.MessageHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertFalse;
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
    public void registerHandler() throws Exception {
        // Given
        subject.registerMessage(TestMessage.class);
        TestMessageHandler handler = new TestMessageHandler();

        // When
        subject.registerHandler(TestMessage.class, handler);

        // Then
        Map<Class<? extends Message>, Collection<MessageHandler<? extends Message>>> handlers = subject.getHandlers();
        assertTrue(handlers.containsKey(TestMessage.class));

        Collection<MessageHandler<? extends Message>> actualHandlers = handlers.get(TestMessage.class);
        assertFalse(actualHandlers.isEmpty());
        assertTrue(actualHandlers.contains(handler));
    }

    public class TestMessage implements Message {}

    public class TestMessageHandler implements MessageHandler<TestMessage> {

        @Override
        public void handle(TestMessage message) {}
    }
}
