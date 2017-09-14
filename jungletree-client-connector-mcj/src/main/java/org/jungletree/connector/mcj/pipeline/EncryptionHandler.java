package org.jungletree.connector.mcj.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.util.List;

public class EncryptionHandler extends MessageToMessageCodec<ByteBuf, ByteBuf> {

    private static final Logger log = LoggerFactory.getLogger(EncryptionHandler.class);

    private final CryptBuf encodeBuf;
    private final CryptBuf decodeBuf;

    public EncryptionHandler(SecretKey sharedSecret) {
        try {
            this.encodeBuf = new CryptBuf(Cipher.ENCRYPT_MODE, sharedSecret);
            this.decodeBuf = new CryptBuf(Cipher.DECRYPT_MODE, sharedSecret);
        } catch (GeneralSecurityException ex) {
            log.error("Failed to initialize encrypted channel", ex);
            throw new AssertionError("Failed to initialize encrypted channel", ex);
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        encodeBuf.crypt(msg, out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        decodeBuf.crypt(msg, out);
    }

    private static class CryptBuf {

        private static final String CIPHER_TYPE = "AES/CFB8/NoPadding";

        private final Cipher cipher;

        private CryptBuf(int mode, SecretKey sharedSecret) throws GeneralSecurityException {
            cipher = Cipher.getInstance(CIPHER_TYPE);
            cipher.init(mode, sharedSecret, new IvParameterSpec(sharedSecret.getEncoded()));
        }

        public void crypt(ByteBuf msg, List<Object> out) {
            ByteBuffer outBuffer = ByteBuffer.allocate(msg.readableBytes());

            try {
                cipher.update(msg.nioBuffer(), outBuffer);
            } catch (ShortBufferException ex) {
                throw new AssertionError("Encryption buffer was too short", ex);
            }

            outBuffer.flip();
            out.add(Unpooled.wrappedBuffer(outBuffer));
        }
    }
}
