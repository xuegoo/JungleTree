package org.jungletree.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import org.jungletree.rainforest.auth.messages.GetServerTokenMessage;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.jungletree.rainforest.util.Messengers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;
import java.util.Base64;

public class GetServerTokenHandler implements MessageHandler<GetServerTokenMessage> {

    private static final Logger log = LoggerFactory.getLogger(GetServerTokenHandler.class);

    private static final int CLIENT_SALT_BYTES = 16;

    private static final Gson GSON = new GsonBuilder().create();

    private final KeyPair serverKeyPair;
    private final JWSHeader serverTokenHeader;
    private final SecureRandom secureRandom;
    private final MessagingService messaging;

    public GetServerTokenHandler(KeyPair serverKeyPair, JWSHeader serverTokenHeader, SecureRandom secureRandom, MessagingService messaging) {
        this.serverKeyPair = serverKeyPair;
        this.serverTokenHeader = serverTokenHeader;
        this.secureRandom = secureRandom;
        this.messaging = messaging;
    }

    @Override
    public void handle(GetServerTokenMessage message) {
        if (!message.getRecipient().equals(Messengers.AUTHENTICATION)) {
            return;
        }

        String salt = generateSalt();
        JWSObject serverToken = new JWSObject(serverTokenHeader, new Payload(createSaltJson(salt)));
        try {
            serverToken.sign(new ECDSASigner((ECPrivateKey) serverKeyPair.getPrivate()));
        } catch (JOSEException ex) {
            log.error("Failed to sign server token!", ex);
            throw new RuntimeException(ex);
        }

        GetServerTokenMessage response = new GetServerTokenMessage();
        response.setSender(Messengers.AUTHENTICATION);
        response.setRecipient(message.getSender());
        response.setLoginRequestId(message.getLoginRequestId());
        response.setClientSalt(salt);
        response.setServerToken(serverToken.serialize());

        log.info(GSON.toJson(response));

        messaging.sendMessage(response);
    }

    private String createSaltJson(String salt) {
        JsonObject claims = new JsonObject();
        claims.addProperty("salt", salt);
        return GSON.toJson(claims);
    }

    private String generateSalt() {
        return Base64.getEncoder().encodeToString(secureRandom.generateSeed(CLIENT_SALT_BYTES));
    }
}
