package org.jungletree.clientconnector.mcb;

import io.gomint.jraknet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

public class BatchedMessageWriter {

    private static final Logger log = LoggerFactory.getLogger(BatchedMessageWriter.class);

    private static final int PACKET_BATCH_ID = 0xFE;

    private final ConnectivityManager connectivityManager;
    private final Deflater deflater;

    private ByteArrayOutputStream out;

    public BatchedMessageWriter(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
        this.out = new ByteArrayOutputStream();
        this.deflater = new Deflater();
    }

    public void writeMessages(Packet... packets) {
        for (Packet packet : packets) {
            log.info("Writing out: {}", packet);
            writeMessage(packet);
        }
    }

    private void writeMessage(Packet packet) {
        PacketBuffer buf = new PacketBuffer(64);
        buf.writeByte(packet.getId());
        buf.writeByte(packet.getSenderSubClientId());
        buf.writeByte(packet.getTargetSubClientId());

        Codec<? extends Packet> codec = connectivityManager.getReg().getCodec(packet.getClass());
        codec.encode(packet, buf);

        writeVarInt(buf.getPosition());

        int bufferLength = buf.getPosition() - buf.getBufferOffset();
        out.write(buf.getBuffer(), buf.getBufferOffset(), bufferLength);
    }

    public void reset() {
        this.out = new ByteArrayOutputStream();
        this.deflater.reset();
        this.deflater.setInput(new byte[0]);
    }

    public byte[] getBatch() {
        byte[] messageBytes = getMessageBytes();

        ByteBuf buf = ByteBufAllocator.DEFAULT.directBuffer(messageBytes.length + 1);
        buf.writeByte(PACKET_BATCH_ID);
        buf.writeBytes(messageBytes);

        byte[] result = new byte[buf.readableBytes()];
        buf.readBytes(result);

        return result;
    }

    private byte[] getMessageBytes() {
        byte[] input = out.toByteArray();
        this.deflater.setInput(input);
        this.deflater.finish();

        out.reset();
        byte[] intermediate = new byte[1024];
        while (!this.deflater.finished()) {
            int read = this.deflater.deflate(intermediate);
            out.write(intermediate, 0, read);
        }

        return out.toByteArray();
    }

    private void writeVarInt(int value) {
        int result = value;

        while ((result & Integer.toUnsignedLong(0x80)) != 0) {
            out.write(result & 0x7f | 0x80);
            result >>>= 0x7;
        }
        out.write(result);
    }
}
