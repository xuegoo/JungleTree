package im.octo.jungletree.network.codec.login;

import im.octo.jungletree.network.message.login.EncryptionKeyResponseMessage;
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
public class EncryptionKeyResponseCodecTest {

    private static final int RANDOM_BYTES_LENGTH = 64;
    private static final Random RANDOM = ThreadLocalRandom.current();

    private ByteBuf buf;
    private EncryptionKeyResponseCodec subject;

    private byte[] sharedSecret;
    private byte[] verifyToken;

    @Before
    public void setUp() throws Exception {
        this.buf = ByteBufAllocator.DEFAULT.buffer();
        this.subject = new EncryptionKeyResponseCodec();

        sharedSecret = new byte[RANDOM_BYTES_LENGTH];
        RANDOM.nextBytes(sharedSecret);

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
        writeVarInt(buf, sharedSecret.length);
        buf.writeBytes(sharedSecret);

        writeVarInt(buf, verifyToken.length);
        buf.writeBytes(verifyToken);

        // When
        EncryptionKeyResponseMessage result = subject.decode(buf);

        // Then
        assertEquals(sharedSecret.length, result.getSharedSecret().length);
        assertArrayEquals(sharedSecret, result.getSharedSecret());

        assertEquals(verifyToken.length, result.getVerifyToken().length);
        assertArrayEquals(verifyToken, result.getVerifyToken());
    }

    @Test
    public void encode() throws Exception {
        // Given
        EncryptionKeyResponseMessage message = new EncryptionKeyResponseMessage(
                sharedSecret, verifyToken
        );

        // When
        subject.encode(buf, message);

        // Then
        byte[] sharedSecret = new byte[readVarInt(buf)];
        buf.readBytes(sharedSecret);
        assertArrayEquals(message.getSharedSecret(), sharedSecret);

        byte[] verifyToken = new byte[readVarInt(buf)];
        buf.readBytes(verifyToken);
        assertArrayEquals(message.getVerifyToken(), verifyToken);
    }
}
