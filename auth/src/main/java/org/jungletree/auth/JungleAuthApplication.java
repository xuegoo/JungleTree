package org.jungletree.auth;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jungletree.rainforest.auth.messages.GetServerTokenMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthRequestMessage;
import org.jungletree.rainforest.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public class JungleAuthApplication {

    private static final Logger log = LoggerFactory.getLogger(JungleAuthApplication.class);

    private final MessagingService messaging;
    private final JwtAuthRequestHandler authHandler;
    private final GetServerTokenHandler serverTokenHandler;

    private final KeyPair serverKeyPair;
    private final JWSHeader serverTokenHeader;

    private JungleAuthApplication() {
        Security.addProvider(new BouncyCastleProvider());

        this.messaging = ServiceLoader.load(MessagingService.class).findFirst().orElseThrow(NoSuchElementException::new);
        messaging.listenForJvmShutdown();

        this.serverKeyPair = generateKeyPair();
        this.serverTokenHeader = createServerTokenHeader((ECPublicKey) serverKeyPair.getPublic());

        this.authHandler = new JwtAuthRequestHandler(messaging);
        this.serverTokenHandler = new GetServerTokenHandler(serverKeyPair, serverTokenHeader, new SecureRandom(), messaging);

        registerMessageHandlers();
        log.info("Started! Listening for incoming token validation requests.");
    }

    private void registerMessageHandlers() {
        log.info("Registering message handlers");
        messaging.start();
        messaging.registerMessage(JwtAuthRequestMessage.class);
        messaging.registerHandler(JwtAuthRequestMessage.class, authHandler);
        messaging.registerMessage(JwtAuthReponseMessage.class);

        messaging.registerMessage(GetServerTokenMessage.class);
        messaging.registerHandler(GetServerTokenMessage.class, serverTokenHandler);
    }

    private KeyPair generateKeyPair()  {
        try {
            // Java public key default is X509, and private key defaults to PKCS#8
            KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
            generator.initialize(384, new SecureRandom());
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            log.error("Could not generate server key pair!", ex);

            // TODO: Wrap RuntimeException for easier debugging
            throw new RuntimeException(ex);
        }
    }

    private JWSHeader createServerTokenHeader(ECPublicKey serverPublicKey) {
        if (!serverPublicKey.getFormat().equals("X.509")) {
            throw new RuntimeException(String.format(
                    "Server public key is not in X.509 format! Got %s instead",
                    serverPublicKey.getFormat()
            ));
        }

        return new JWSHeader(
                JWSAlgorithm.ES384,
                null,
                null,
                null,
                null,
                null,
                URI.create(getServerPublicKeyBase64(serverPublicKey)),
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private String getServerPublicKeyBase64(ECPublicKey serverPublicKey) {
        return Base64.getEncoder().encodeToString(serverPublicKey.getEncoded());
    }

    public static void main(String[] args) {
        new JungleAuthApplication();
    }
}
