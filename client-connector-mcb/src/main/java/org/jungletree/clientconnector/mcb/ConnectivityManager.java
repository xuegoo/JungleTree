package org.jungletree.clientconnector.mcb;

import io.gomint.jraknet.EncapsulatedPacket;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.jraknet.Socket;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.codec.handshake.LoginCodec;
import org.jungletree.clientconnector.mcb.handler.PacketHandler;
import org.jungletree.clientconnector.mcb.handler.handshake.LoginHandler;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.jungletree.clientconnector.mcb.packet.handshake.LoginPacket;
import org.jungletree.rainforest.scheduler.Scheduler;
import org.jungletree.rainforest.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.InflaterInputStream;

public class ConnectivityManager implements Runnable {

    private static final byte PACKET_BATCH = (byte) 0xfe;

    private static final Logger log = LoggerFactory.getLogger(ConnectivityManager.class);

    private final Scheduler scheduler;

    private final BedrockServer server;
    private final CodecRegistration codecRegistration;

    private ScheduledFuture task;

    public ConnectivityManager(BedrockServer server) {
        this.scheduler = SchedulerService.getInstance().getScheduler();
        this.server = server;
        this.codecRegistration = new CodecRegistration();

        registerCodecs();
    }

    public CodecRegistration getCodecRegistration() {
        return codecRegistration;
    }

    public void start() {
        this.task = scheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (isRunning()) {
            this.task.cancel(false);
        }
    }

    private boolean isRunning() {
        return task != null && !task.isCancelled();
    }

    @Override
    public void run() {
        Map<Socket, ClientConnection> connections = server.getConnections();
        connections.values()
                .forEach(client -> {
                    EncapsulatedPacket packet = client.getConnection().receive();
                    if (packet == null) {
                        return;
                    }

                    PacketBuffer buf = new PacketBuffer(packet.getPacketData(), 0);

                    byte id = buf.readByte();
                    if (id == PACKET_BATCH) {
                        handleBatchPacket(client, buf);
                    } else if (id == 0x04) {
                        handleMessage(client, buf, id);
                    } else {
                        handleSocketData(client, buf);
                    }
                });
    }

    private void registerCodecs() {
        codecRegistration.codec(0x01, LoginPacket.class, LoginCodec.class);
        codecRegistration.handler(LoginPacket.class, new LoginHandler(server));
    }

    private void handleBatchPacket(ClientConnection client, PacketBuffer buf) {
        byte[] input = new byte[buf.getRemaining()];
        System.arraycopy(buf.getBuffer(), buf.getPosition(), input, 0, input.length);

        /*if (client.isEncryptionEnabled()) {
            input = client.getProtocolEncryption().decryptInput(input);
            if (input == null) {
                client.getConnection().disconnect("Protocol encryption: invalid checksum");
                return;
            }
        }*/

        byte[] payload = deflate(buf, input);

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

    private byte[] deflate(PacketBuffer buf, byte[] input) {
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
            return null;
        }
        return out.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private void handleSocketData(ClientConnection client, PacketBuffer buf) {
        int id = buf.readUnsignedVarInt();
        log.info("Read packet ID 0x{}", Integer.toHexString(id));

        byte senderSubClientId = buf.readByte();
        byte targetSubClientId = buf.readByte();

        Class<Packet> messageClass = codecRegistration.getMessageClass(id);
        Codec codec = codecRegistration.getCodec(messageClass);
        Packet packet = codec.decode(buf);
        packet.setSenderSubClientId(senderSubClientId);
        packet.setTargetSubClientId(targetSubClientId);

        PacketHandler handler = codecRegistration.getHandler(messageClass);
        handler.handle(client, packet);
    }

    @SuppressWarnings("unchecked")
    private void handleMessage(ClientConnection client, PacketBuffer buf, byte id) {
        log.info("Read packet ID 0x{}", Integer.toHexString(id));
        Class<Packet> messageClass = codecRegistration.getMessageClass(id);
        Codec codec = codecRegistration.getCodec(messageClass);
        Packet packet = codec.decode(buf);

        PacketHandler handler = codecRegistration.getHandler(messageClass);
        handler.handle(client, packet);
    }
}
