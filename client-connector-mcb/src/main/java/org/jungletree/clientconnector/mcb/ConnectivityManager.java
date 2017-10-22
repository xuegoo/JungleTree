package org.jungletree.clientconnector.mcb;

import io.gomint.jraknet.EncapsulatedPacket;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.Socket;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.handler.MessageHandler;
import org.jungletree.clientconnector.mcb.message.Message;
import org.jungletree.clientconnector.mcb.protocol.Protocol;
import org.jungletree.rainforest.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.zip.InflaterInputStream;

public class ConnectivityManager implements Runnable {

    private static final byte PACKET_BATCH = (byte) 0xfe;

    private static final Logger log = LoggerFactory.getLogger(ConnectivityManager.class);

    private final SchedulerService scheduler;
    private final BedrockServer server;

    private volatile org.jungletree.clientconnector.mcb.protocol.Protocol protocol;

    private volatile boolean running = false;

    public ConnectivityManager(BedrockServer server) {
        this.scheduler = ServiceLoader.load(SchedulerService.class).findFirst().orElseThrow(NoSuchElementException::new);
        this.server = server;
        this.protocol = new Protocol(server.getMessagingService());
    }

    public org.jungletree.clientconnector.mcb.protocol.Protocol getProtocol() {
        return protocol;
    }

    public void start() {
        this.running = true;
        new Thread(this, "ConnectivityManager Thread").start();
    }

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    // TODO: Implement scheduling of repeating tasks
    @Override
    public void run() {
        while (running) {
            scheduler.execute(() -> {
                Map<Socket, ClientConnection> connections = server.getConnections();
                connections.values().stream()
                        .forEach(client -> {
                            EncapsulatedPacket packet = client.getConnection().receive();
                            PacketBuffer buf = new PacketBuffer(packet.getPacketData(), 0);

                            byte id = buf.readByte();
                            if (id == PACKET_BATCH) {
                                handleBatchPacket(client, buf);
                            } else {
                                handleSocketData(client, buf);
                            }
                        });
            });

            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
        }
    }

    private void handleBatchPacket(ClientConnection client, PacketBuffer buf) {
        byte[] input = new byte[buf.getRemaining()];
        System.arraycopy(buf.getBuffer(), buf.getPosition(), input, 0, input.length);

        InflaterInputStream inflater = new InflaterInputStream(new ByteArrayInputStream(input));
        ByteArrayOutputStream out = new ByteArrayOutputStream(buf.getRemaining());
        byte[] batchIntermediate = new byte[256];

        try {
            int read;
            while ((read = inflater.read(batchIntermediate)) > -1) {
                out.write(batchIntermediate, 0, read);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        byte[] payload = out.toByteArray();

        PacketBuffer buffer = new PacketBuffer(payload, 0);
        while (buffer.getRemaining() > 0) {
            int packetLength = buffer.readUnsignedVarInt();

            byte[] payloadData = new byte[packetLength];
            buffer.readBytes(payloadData);

            PacketBuffer pktBuffer = new PacketBuffer(payloadData, 0);
            handleSocketData(client, pktBuffer);

            if (pktBuffer.getRemaining() > 0) {
                pktBuffer.resetPosition();
                log.warn("Did not read packet ID 0x{} to completion", Integer.toHexString(pktBuffer.readByte()));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void handleSocketData(ClientConnection client, PacketBuffer buf) {
        int id = buf.readUnsignedVarInt();
        log.info("Read packet ID 0x{}", Integer.toHexString(id));

        byte senderSubClientId = buf.readByte();
        byte targetSubClientId = buf.readByte();

        CodecRegistration codecRegistration = protocol.getCodecRegistration();

        Class<Message> messageClass = codecRegistration.getMessageClass(id);
        Codec codec = protocol.getCodecRegistration().getCodec(messageClass);
        Message message = codec.decode(buf);
        message.setSenderSubClientId(senderSubClientId);
        message.setTargetSubClientId(targetSubClientId);

        MessageHandler handler = codecRegistration.getHandler(messageClass);
        handler.handle(client, message);
    }
}
