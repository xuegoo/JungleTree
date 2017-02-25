package im.octo.jungletree.network.codec.login;

import im.octo.jungletree.network.message.login.EncryptionKeyRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.flowpowered.network.util.ByteBufUtils.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EncryptionKeyRequestCodecTest {

    private static final String SERVER_ID = "serverId";
    private static final int RANDOM_BYTES_LENGTH = 64;
    private static final Random RANDOM = ThreadLocalRandom.current();

    private ByteBuf buf;
    private EncryptionKeyRequestCodec subject;

    private byte[] publicKey;
    private byte[] verifyToken;

    @Before
    public void setUp() throws Exception {
        this.buf = ByteBufAllocator.DEFAULT.buffer();
        this.subject = new EncryptionKeyRequestCodec();

        publicKey = new byte[RANDOM_BYTES_LENGTH];
        RANDOM.nextBytes(publicKey);

        verifyToken = new byte[RANDOM_BYTES_LENGTH];
        RANDOM.nextBytes(verifyToken);
    }

    @After
    public void tearDown() throws Exception {
        this.buf = null;
        this.subject = null;
    }

    @Test
    public void decode() throws Exception {
        // Given
        writeUTF8(buf, SERVER_ID);

        writeVarInt(buf, publicKey.length);
        buf.writeBytes(publicKey);

        writeVarInt(buf, verifyToken.length);
        buf.writeBytes(verifyToken);

        // When
        EncryptionKeyRequestMessage result = subject.decode(buf);

        // Then
        assertEquals(SERVER_ID, result.getServerId());

        assertEquals(publicKey.length, result.getPublicKey().length);
        assertArrayEquals(publicKey, result.getPublicKey());

        assertEquals(verifyToken.length, result.getVerifyToken().length);
        assertArrayEquals(verifyToken, result.getVerifyToken());
    }

    @Test
    public void encode() throws Exception {
        // Given
        EncryptionKeyRequestMessage message = new EncryptionKeyRequestMessage(
                SERVER_ID, publicKey, verifyToken
        );

        // When
        subject.encode(buf, message);

        // Then
        assertEquals(message.getServerId(), readUTF8(buf));

        byte[] publicKey = new byte[readVarInt(buf)];
        buf.readBytes(publicKey);
        assertArrayEquals(message.getPublicKey(), publicKey);

        byte[] verifyToken = new byte[readVarInt(buf)];
        buf.readBytes(verifyToken);
        assertArrayEquals(message.getVerifyToken(), verifyToken);
    }
}
