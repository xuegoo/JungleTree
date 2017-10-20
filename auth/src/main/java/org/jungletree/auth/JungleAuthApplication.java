package org.jungletree.auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthRequestMessage;
import org.jungletree.rainforest.messaging.MessageHandler;
import org.jungletree.rainforest.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.*;

import static org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage.AuthenticationStatus.*;

public class JungleAuthApplication implements MessageHandler<JwtAuthRequestMessage> {

    private static final Logger log = LoggerFactory.getLogger(JungleAuthApplication.class);

    private static final String MOJANG_PUBLIC_KEY = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V";

    private static final DefaultJWSVerifierFactory VERIFIER_FACTORY = new DefaultJWSVerifierFactory();

    private final MessagingService messaging;

    private JungleAuthApplication() {
        this.messaging = ServiceLoader.load(MessagingService.class).findFirst().orElseThrow(NoSuchElementException::new);

        registerMessageHandlers();
        log.info("Started! Listening for incoming token validation requests.");
    }

    private static ECPublicKey getMojangPublicKey() {
        return getECX509PublicKey(MOJANG_PUBLIC_KEY);
    }

    private static ECPublicKey getECX509PublicKey(String base64Key) {
        try {
            return (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(base64Key)));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void registerMessageHandlers() {
        log.trace("Registering message handlers");
        messaging.start();
        messaging.registerMessage(JwtAuthRequestMessage.class);
        messaging.registerHandler(JwtAuthRequestMessage.class, this);
        messaging.registerMessage(JwtAuthReponseMessage.class);
    }

    @Override
    public void handle(JwtAuthRequestMessage message) {
        log.trace("Inbound JWT validation request: {}", message.toString());
        String[] chain = message.getChain();
        String token = message.getJwtToken();

        JwtAuthReponseMessage response = new JwtAuthReponseMessage();

        if (!validateCertificateChain(response, chain)) {
            response.setStatus(INVALID_CERTIFICATE_CHAIN);
            log.info(response.toString());
            return;
        }

        Optional<JWSObject> jwsTokenOptional = getJwsToken(token);
        if (!jwsTokenOptional.isPresent()) {
            response.setStatus(INVALID_TOKEN);
            messaging.sendMessage(response);
            log.info(response.toString());
            return;
        }

        response.setStatus(validatePublicKey(jwsTokenOptional.get()));
        messaging.sendMessage(response);
        log.info(response.toString());
    }

    private boolean validateCertificateChain(JwtAuthReponseMessage response, String... chain) {
        if (!validateCertificateChainIsValid(chain)) {
            response.setStatus(INVALID_CERTIFICATE_CHAIN);
            messaging.sendMessage(response);
            return false;
        }

        if (Arrays.stream(chain).noneMatch(this::verifyContainsMojangRootPublicKey)) {
            response.setStatus(INVALID_CERTIFICATE_CHAIN);
            messaging.sendMessage(response);
            return false;
        }
        return true;
    }

    private boolean validateCertificateChainIsValid(String... chain) {
        return Arrays.stream(chain).allMatch(this::verifyCertificateIsValid);
    }

    private boolean verifyCertificateIsValid(String token) {
        try {
            JWSObject jwsToken = JWSObject.parse(token);
            jwsToken.verify(VERIFIER_FACTORY.createJWSVerifier(jwsToken.getHeader(), getMojangPublicKey()));
        } catch (ParseException | JOSEException ex) {
            return false;
        }
        return true;
    }

    private boolean verifyContainsMojangRootPublicKey(String token) {
        try {
            JWSObject jwsToken = JWSObject.parse(token);
            return jwsToken.verify(VERIFIER_FACTORY.createJWSVerifier(jwsToken.getHeader(), getMojangPublicKey()));
        } catch (ParseException | JOSEException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Optional<JWSObject> getJwsToken(String token) {
        try {
            return Optional.of(JWSObject.parse(token));
        } catch (ParseException ex) {
            return Optional.empty();
        }
    }

    private JwtAuthReponseMessage.AuthenticationStatus validatePublicKey(JWSObject jwsToken) {
        JWSVerifier verifier;
        try {
            verifier = VERIFIER_FACTORY.createJWSVerifier(jwsToken.getHeader(), getECX509PublicKey(jwsToken.getHeader().getX509CertURL().toString()));
        } catch (JOSEException e) {
            return INVALID_TOKEN;
        }

        try {
            return jwsToken.verify(verifier) ? OK : INVALID_TOKEN_SIGNATURE;
        } catch (JOSEException e) {
            return INVALID_TOKEN;
        }
    }

    public static void main(String[] args) {
        new JungleAuthApplication();
    }
}
