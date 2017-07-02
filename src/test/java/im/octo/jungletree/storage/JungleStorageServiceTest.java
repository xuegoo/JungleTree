package im.octo.jungletree.storage;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import im.octo.jungletree.messaging.message.ContainerMessage;
import im.octo.jungletree.rainforest.messaging.Message;
import im.octo.jungletree.rainforest.messaging.MessagingService;
import im.octo.jungletree.rainforest.storage.Container;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JungleStorageServiceTest {

    @Mock
    private MessagingService messagingService;

    @Mock
    private ContainerMessage containerMessage;

    @Mock
    private Container container;

    private JungleStorageService subject;

    @Before
    public void setUp() throws Exception {
        when(messagingService.sendMessage(any(Message.class))).thenReturn(CompletableFuture.supplyAsync(() -> containerMessage));
        when(containerMessage.getContainer()).thenReturn(container);
        this.subject = new JungleStorageService();
        Guice.createInjector(new TestGuiceModule()).injectMembers(subject);
    }

    @After
    public void tearDown() throws Exception {
        this.subject = null;
    }

    @Test
    public void open() throws Exception {
        // Given
        CompletableFuture<Container> result;

        // When
        result = subject.open("test");

        // Then
        assertNotNull(result);
        assertNotNull(result.get());
    }

    @Test
    public void close() throws Exception {
        // Given
        Container container = subject.open("test").get();

        // When
        subject.close(container);
    }

    class TestGuiceModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(MessagingService.class).toInstance(messagingService);
        }
    }
}
