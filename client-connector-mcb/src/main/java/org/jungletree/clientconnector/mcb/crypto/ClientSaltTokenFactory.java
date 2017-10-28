package org.jungletree.clientconnector.mcb.crypto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.ECDSASigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;
import java.util.Base64;

public class ClientSaltTokenFactory {

    private static final Logger log = LoggerFactory.getLogger(ClientSaltTokenFactory.class);

    private static final int CLIENT_SALT_BYTES = 16;
    private static final Gson GSON = new GsonBuilder().create();

    private final KeyPair serverKeyPair;
    private final JWSHeader serverTokenHeader;

    public ClientSaltTokenFactory(KeyPair serverKeyPair, JWSHeader serverTokenHeader) {
        this.serverKeyPair = serverKeyPair;
        this.serverTokenHeader = serverTokenHeader;
    }

    public ServerToken generateClientSaltToken() {
        byte[] clientSalt = generateSalt();
        JWSObject serverToken = new JWSObject(serverTokenHeader, new Payload(createClientSaltJson(clientSalt)));
        try {
            serverToken.sign(new ECDSASigner((ECPrivateKey) serverKeyPair.getPrivate()));
        } catch (JOSEException ex) {
            log.error("Failed to sign server token!", ex);
            throw new RuntimeException(ex);
        }

        ServerToken result = new ServerToken();
        result.setClientSalt(clientSalt);
        result.setServerToken(serverToken.serialize());

        return result;
    }

    private String createClientSaltJson(byte[] clientSalt) {
        JsonObject claims = new JsonObject();
        claims.addProperty("salt", Base64.getEncoder().encodeToString(clientSalt));
        return GSON.toJson(claims);
    }

    private byte[] generateSalt() {
        return new SecureRandom().generateSeed(CLIENT_SALT_BYTES);
    }
}
