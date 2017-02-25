package im.octo.jungletree.network.codec.handshake;

import im.octo.jungletree.api.GameVersion;
import im.octo.jungletree.network.message.handshake.HandshakeMessage;
import im.octo.jungletree.network.protocol.HandshakeState;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.flowpowered.network.util.ByteBufUtils.*;
import static com.flowpowered.network.util.ByteBufUtils.writeVarInt;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class HandshakeCodecTest {

    private static final Logger log = LoggerFactory.getLogger(HandshakeCodecTest.class);

    private static final GameVersion GAME_VERSION = GameVersion.MC_1_11_2;
    private static final String ADDRESS = "localhost";
    private static final int PORT = 25565;
    private static final HandshakeState STATE = HandshakeState.STATUS;

    private ByteBuf buf;
    private HandshakeCodec subject;

    @Before
    public void setUp() throws Exception {
        buf = ByteBufAllocator.DEFAULT.buffer();
        this.subject = new HandshakeCodec();
    }

    @After
    public void tearDown() throws Exception {
        this.buf = null;
        this.subject = null;
    }

    @Test
    public void decode() throws Exception {
        // Given
        writeVarInt(buf, GAME_VERSION.getProtocolVersion());
        writeUTF8(buf, ADDRESS);
        buf.writeShort(PORT);
        writeVarInt(buf, STATE.getId());

        // When
        HandshakeMessage result = subject.decode(buf);

        // Then
        assertEquals(GAME_VERSION.getProtocolVersion(), result.getProtocolVersion());
        assertEquals(ADDRESS, result.getAddress());
        assertEquals(PORT, result.getPort());
        assertEquals(STATE.getId(), result.getState());
    }

    @Test
    public void encode() throws Exception {
        // When
        subject.encode(buf, new HandshakeMessage(GAME_VERSION.getProtocolVersion(), ADDRESS, PORT, STATE.getId()));

        // Then
        assertEquals(GAME_VERSION.getProtocolVersion(), readVarInt(buf));
        assertEquals(ADDRESS, readUTF8(buf));
        assertEquals(PORT, buf.readUnsignedShort());
        assertEquals(STATE.getId(), readVarInt(buf));
    }
}
