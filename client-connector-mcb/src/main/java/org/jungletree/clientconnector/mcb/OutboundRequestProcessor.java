package org.jungletree.clientconnector.mcb;

import org.jungletree.clientconnector.mcb.packet.Packet;
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

    private final BatchedMessageWriter writer;
    private final ClientConnection client;
    private final ScheduledExecutorService executorService;

    private Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();

    public OutboundRequestProcessor(ConnectivityManager connectivityManager, ClientConnection client) {
        this.client = client;
        this.executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        this.writer = new BatchedMessageWriter(connectivityManager);

        Runtime.getRuntime().addShutdownHook(new Thread(OutboundRequestProcessor.this::stop));
        start();
    }

    public void batch(Packet packet) {
        log.info("Queued {}", packet.toString());
        packetQueue.add(packet);
    }

    private void start() {
        executorService.scheduleAtFixedRate(this::flush, UPDATE_FREQUENCY_MILLIS, UPDATE_FREQUENCY_MILLIS, TimeUnit.MILLISECONDS);
    }

    public void flush() {
        if (packetQueue.isEmpty()) {
            return;
        }

        Packet[] packets = pollMessages();
        for (Packet packet : packets) {
            packetQueue.remove(packet);
        }

        if (packets.length == 0) {
            return;
        }

        writer.writeMessages(packets);
        byte[] batch = writer.getBatch();
        writer.reset();

        if (client.isEncryptionEnabled()) {
            batch = client.getProtocolEncryption().encryptInputForClient(batch);
            log.info("Sending data as encrypted");
        }

        log.info("Sending batch packet");
        client.getConnection().send(batch);
    }

    private synchronized void stop() {
        if (!executorService.isShutdown() || !executorService.isTerminated()) {
            this.executorService.shutdown();
        }
    }

    private Packet[] pollMessages() {
        int count = packetQueue.size() >= MAX_PACKETS_PER_BATCH ? MAX_PACKETS_PER_BATCH : packetQueue.size();
        Packet[] result = new Packet[count];
        for (int i=0; i<result.length; i++) {
            result[i] = packetQueue.poll();
        }
        return result;
    }
}
