package org.jungletree.connector.mcj.handler.login;

import com.flowpowered.network.MessageHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.http.HttpCallback;
import org.jungletree.connector.mcj.http.HttpClient;
import org.jungletree.connector.mcj.message.login.EncryptionKeyResponseMessage;
import org.jungletree.network.ClientConnectorResourceService;
import org.jungletree.rainforest.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.util.Arrays;

public class EncryptionKeyResponseHandler implements MessageHandler<JSession, EncryptionKeyResponseMessage> {

    private static final Logger log = LoggerFactory.getLogger(EncryptionKeyResponseHandler.class);

    private static final String CIPHER_TYPE = "RSA";
    private static final String CIPHER_ALGORITHM = "AES";
    private static final String DIGEST_ALGORITHM = "SHA-1";
    private static final String SESSION_SERVER_URL = "https://sessionserver.mojang.com/session/minecraft/hasJoined?username=%s&serverId=%s";

    private final Gson gson;
    private final ClientConnectorResourceService resource;
    private SchedulerService scheduler;

    public EncryptionKeyResponseHandler(ClientConnectorResourceService resource) {
        this.gson = new GsonBuilder().create();
        this.resource = resource;
    }

    public void setScheduler(SchedulerService scheduler) {
        if (this.scheduler != null) {
            throw new IllegalStateException("Scheduler already declared");
        }
        this.scheduler = scheduler;
    }

    @Override
    public void handle(JSession session, EncryptionKeyResponseMessage message) {
        PrivateKey privateKey = resource.getKeyPair().getPrivate();

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(CIPHER_TYPE);
        } catch (GeneralSecurityException ex) {
            log.error("Initialization of RSA cipher failed:", ex);
            return;
        }

        SecretKey sharedSecret;
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            sharedSecret = new SecretKeySpec(cipher.doFinal(message.getSharedSecret()), CIPHER_ALGORITHM);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            log.warn("Could not decrypt shared secret:", ex);
            session.disconnect("Unable to decrypt shared secret");
            return;
        }

        byte[] verifyToken;
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            verifyToken = cipher.doFinal(message.getVerifyToken());
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
            log.warn("Could not decrypt verify token", ex);
            session.disconnect("Unable to decrypt verify token");
            return;
        }

        if (!Arrays.equals(verifyToken, session.getVerifyToken())) {
            session.disconnect("Invalid verify token.");
            return;
        }

        session.enableEncryption(sharedSecret);

        String hash;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            log.error("Unable to generate SHA-1 digest", ex);
            session.disconnect("Failed to hash login data.");
            return;
        }
        digest.update(session.getSessionId().getBytes());
        digest.update(sharedSecret.getEncoded());
        digest.update(resource.getKeyPair().getPublic().getEncoded());

        hash = new BigInteger(digest.digest()).toString(16);
        String url = getSessionServerUrl(session.getVerifyUsername(), hash);
        HttpClient.connect(url, session.getChannel().eventLoop(), new ClientAuthCallback(session));
    }

    private String getSessionServerUrl(String verifyUsername, String hash) {
        return String.format(SESSION_SERVER_URL, verifyUsername, hash);
    }

    private class ClientAuthCallback implements HttpCallback {

        private final JSession session;

        public ClientAuthCallback(JSession session) {
            this.session = session;
        }

        @Override
        public void done(String response) {
            if (response == null || response.isEmpty()) {
                log.warn("No response received from the session server. Is the Mojang session server down?");
                session.disconnect("Failed to verify username!");
            }

            /* AuthPlayer player;
            try {
                player = gson.fromJson(response, AuthPlayer.class);
            } catch (JsonSyntaxException ex) {
                log.warn("Username \"{}\" failed to authenticate!", session.getVerifyUsername());
                session.disconnect("Failed to verify username!");
                return;
            }

            String username = player.getName();
            UUID uuid;
            try {
                uuid = player.getUuid();
            } catch (IllegalArgumentException ex) {
                log.error("The returned authentication UUID was invalid: " + player.getId(), ex);
                session.disconnect("Invalud UUID.");
                return;
            }

            scheduler.execute(() -> session.setPlayer(new PlayerProfile(uuid, username, player.getProperties())));
            */
        }

        @Override
        public void error(Throwable t) {
            log.error("Error in authentication thread:", t);
            session.disconnect("Internal server error during authentication.", true);
        }
    }
}
