package im.octo.jungletree.scheduler;

import im.octo.jungletree.api.scheduler.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JTaskSchedulerTest {

    private JTaskScheduler subject;

    private AtomicInteger result;

    @Before
    public void setUp() throws Exception {
        this.subject = new JTaskScheduler();
        this.result = new AtomicInteger();
    }

    @After
    public void tearDown() throws Exception {
        subject.shutdown();
        this.subject = null;
        this.result = null;
    }

    @Test
    public void execute() throws Exception {
        // Given
        Task task = () -> result.incrementAndGet();

        // When
        subject.execute(task);

        // Then
        while(result.get() == 0) {
            Thread.sleep(1);
        }
        assertEquals(1, result.get());
    }

    @Test
    public void shutdown() throws Exception {
        // Given
        Task task = () -> result.incrementAndGet();
        subject.execute(task);

        // When
        subject.shutdown();

        // Then
        assertTrue(subject.getExecutor().isShutdown());
    }
}
