package org.jungletree.clientconnector.mcb.crypto;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyAgreement;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

public class ProtocolEncryption {

    private static final Logger log = LoggerFactory.getLogger(ProtocolEncryption.class);

    // Packet counters
    private AtomicLong sendingCounter = new AtomicLong(0);
    private AtomicLong receiveCounter = new AtomicLong(0);

    // Client Side:
    private ECPublicKey clientPublicKey;
    private BufferedBlockCipher clientEncryptor;
    private BufferedBlockCipher clientDecryptor;

    // Data for packet and checksum calculations
    private byte[] clientSalt;
    private byte[] key;

    private final KeyPair serverKeyPair;

    public ProtocolEncryption(KeyPair serverKeyPair) {
        this.serverKeyPair = serverKeyPair;
    }

    public byte[] getClientSalt() {
        return clientSalt;
    }

    public void setClientSalt(byte[] clientSalt) {
        this.clientSalt = clientSalt;
    }

    public void setClientPublicKey(ECPublicKey key) {
        this.clientPublicKey = key;
    }

    public boolean beginClientsideEncryption() {
        if (this.clientEncryptor != null && this.clientDecryptor != null) {
            // Already initialized:
            return true;
        }

        // Generate shared secret from ECDH keys:
        byte[] secret = this.generateECDHSecret( this.serverKeyPair.getPrivate(), this.clientPublicKey );
        if ( secret == null ) {
            return false;
        }

        // Derive key as salted SHA-256 hash digest:
        this.key = this.hashSHA256( this.clientSalt, secret );
        byte[] iv = this.takeBytesFromArray( this.key, 0, 16 );

        // Initialize BlockCiphers:
        this.clientEncryptor = this.createCipher( true, this.key, iv );
        this.clientDecryptor = this.createCipher( false, this.key, iv );
        return true;
    }

    public byte[] decryptInputFromClient(byte[] input) {
        byte[] output = this.processCipher(this.clientDecryptor, input);
        if (output == null) {
            return null;
        }

        byte[] outputChunked = new byte[input.length - 8];

        System.arraycopy(output, 0, outputChunked, 0, outputChunked.length);

        byte[] hashBytes = calcHash(outputChunked, this.receiveCounter);
        for (int i = output.length - 8; i < output.length; i++) {
            if (hashBytes[i - (output.length - 8)] != output[i]) {
                return null;
            }
        }

        return outputChunked;
    }

    public byte[] encryptInputForClient(byte[] input) {
        byte[] hashBytes = calcHash(input, this.sendingCounter);
        byte[] finalInput = new byte[hashBytes.length + input.length];

        System.arraycopy(input, 0, finalInput, 0, input.length);
        System.arraycopy(hashBytes, 0, finalInput, input.length, 8);

        return this.processCipher(this.clientEncryptor, finalInput);
    }

    public String getServerPublic() {
        return Base64.getEncoder().encodeToString(serverKeyPair.getPublic().getEncoded());
    }

    private byte[] calcHash(byte[] input, AtomicLong counter) {
        SHA256Digest digest = new SHA256Digest();

        byte[] result = new byte[digest.getDigestSize()];
        digest.update(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(counter.getAndIncrement()).array(), 0, 8);
        digest.update(input, 0, input.length);
        digest.update(this.key, 0, this.key.length);
        digest.doFinal(result, 0);

        return Arrays.copyOf(result, 8);
    }

    private byte[] processCipher(BufferedBlockCipher cipher, byte[] input) {
        byte[] output = new byte[cipher.getOutputSize(input.length)];
        int cursor = cipher.processBytes(input, 0, input.length, output, 0);

        try {
            // cursor += cipher.doFinal( output, cursor );
            if (cursor != output.length) {
                throw new InvalidCipherTextException("Output size did not match cursor");
            }
        } catch (InvalidCipherTextException e) {
            log.error("Could not encrypt/decrypt to/from cipher-text", e);
            return null;
        }

        return output;
    }

    private byte[] generateECDHSecret(PrivateKey privateKey, PublicKey publicKey) {
        try {
            KeyAgreement ka = KeyAgreement.getInstance("ECDH", "BC");
            ka.init(privateKey);
            ka.doPhase(publicKey, true);
            return ka.generateSecret();
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException e) {
            log.error("Failed to generate Elliptic-Curve-Diffie-Hellman Shared Secret for clientside encryption", e);
            return null;
        }
    }

    private byte[] takeBytesFromArray(byte[] buffer, int offset, int length) {
        byte[] result = new byte[length];
        System.arraycopy(buffer, offset, result, 0, length);
        return result;
    }

    private byte[] hashSHA256(byte[]... message) {
        SHA256Digest digest = new SHA256Digest();

        byte[] result = new byte[digest.getDigestSize()];
        for (byte[] bytes : message) {
            digest.update(bytes, 0, bytes.length);
        }

        digest.doFinal(result, 0);

        return result;
    }

    private BufferedBlockCipher createCipher(boolean encryptor, byte[] key, byte[] iv) {
        BufferedBlockCipher cipher = new BufferedBlockCipher(new CFBBlockCipher(new AESEngine(), 8));
        cipher.init(encryptor, new ParametersWithIV(new KeyParameter(key), iv));
        return cipher;
    }

}
