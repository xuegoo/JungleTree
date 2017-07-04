package org.jungletree.storage;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import org.jungletree.storage.storage.ContainerMessage;
import org.jungletree.rainforest.messaging.Message;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.storage.Container;
import org.jungletree.storage.storage.JungleStorageService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.CompletableFuture;

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
        Mockito.when(messagingService.sendMessage(Matchers.any(Message.class))).thenReturn(CompletableFuture.supplyAsync(() -> containerMessage));
        Mockito.when(containerMessage.getContainer()).thenReturn(container);
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
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get());
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
