package org.jungletree.clientconnector.mcb;

import org.jungletree.clientconnector.mcb.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OutboundRequestProcessor {

    private static final Logger log = LoggerFactory.getLogger(OutboundRequestProcessor.class);

    // TODO: Configurable update rates
    private static final int UPDATE_RATE_SECONDS = 20;
    private static final int UPDATE_FREQUENCY_MILLIS = 1000 / UPDATE_RATE_SECONDS;

    // TODO: Figure out optimum, make that the default in a future default configuration
    private static final int MAX_PACKETS_PER_BATCH = 32;

    private final ClientConnection client;
    private final ScheduledExecutorService executorService;
    private final BatchedMessageWriter writer;

    private volatile boolean switchingProtocols = false;
    private volatile boolean flushing = false;

    private Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    public OutboundRequestProcessor(ConnectivityManager connectivityManager, ClientConnection client) {
        this.client = client;
        this.executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        this.writer = new BatchedMessageWriter(connectivityManager);

        Runtime.getRuntime().addShutdownHook(new Thread(OutboundRequestProcessor.this::stop));
        start();
    }

    public void setSwitchingProtocols(boolean switchingProtocols) {
        this.switchingProtocols = switchingProtocols;
    }

    public void batch(Message message) {
        log.info("Queued {}", message.toString());
        messageQueue.add(message);
    }

    private void start() {
        executorService.scheduleAtFixedRate(() -> {
            if (switchingProtocols || flushing) {
                return;
            }

            if (messageQueue.isEmpty()) {
                return;
            }

            Message[] messages = pollMessages();
            for (Message message : messages) {
                messageQueue.remove(message);
            }

            if (messages.length == 0) {
                return;
            }

            writer.writeMessages(messages);
            byte[] batch = writer.getBatch();
            writer.reset();

            log.info("Sending batch packet");
            client.getConnection().send(batch);

        }, UPDATE_FREQUENCY_MILLIS, UPDATE_FREQUENCY_MILLIS, TimeUnit.MILLISECONDS);
    }

    private synchronized void stop() {
        if (!executorService.isShutdown() || !executorService.isTerminated()) {
            this.executorService.shutdown();
        }
    }

    private Message[] pollMessages() {
        int count = messageQueue.size() >= MAX_PACKETS_PER_BATCH ? MAX_PACKETS_PER_BATCH : messageQueue.size();
        Message[] result = new Message[count];
        for (int i=0; i<result.length; i++) {
            result[i] = messageQueue.poll();
        }
        return result;
    }
}
