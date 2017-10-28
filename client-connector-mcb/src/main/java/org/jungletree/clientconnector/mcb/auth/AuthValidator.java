package org.jungletree.clientconnector.mcb.auth;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import org.jungletree.clientconnector.mcb.ClientConnection;
import org.jungletree.clientconnector.mcb.exception.NotAuthorizedException;
import org.jungletree.clientconnector.mcb.packet.handshake.ConnectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class AuthValidator {

    private static final Logger log = LoggerFactory.getLogger(AuthValidator.class);

    private static final String MOJANG_PUBLIC_KEY = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83" +
            "ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V";

    private static final DefaultJWSVerifierFactory VERIFIER_FACTORY = new DefaultJWSVerifierFactory();

    public void validate(ClientConnection client, ConnectionInfo connectionInfo) throws NotAuthorizedException {
        log.info("Validation: {}", connectionInfo.toString());
        String[] chain = connectionInfo.getTokenChain().getChain();
        String token = connectionInfo.getClientDataToken();

        if (!validateCertificateChain(chain)) {
            throw new NotAuthorizedException("Invalid certificate chain");
        }

        Optional<JWSObject> jwsTokenOptional = getJwsToken(token);
        if (!jwsTokenOptional.isPresent()) {
            throw new NotAuthorizedException("Invalid token");
        }

        JWSObject jwtToken = jwsTokenOptional.get();
        if (!validatePublicKey(jwtToken)) {
            throw new NotAuthorizedException("Invalid public key");
        }
    }

    private static ECPublicKey getMojangPublicKey() {
        return getECX509PublicKey(MOJANG_PUBLIC_KEY);
    }

    private static ECPublicKey getECX509PublicKey(String base64Key) {
        try {
            return (ECPublicKey) KeyFactory.getInstance("EC", "BC").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(base64Key)));
        } catch (InvalidKeySpecException | NoSuchProviderException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean validateCertificateChain(String... chain) {
        if (!validateCertificateChainIsValid(chain)) {
            return false;
        }

        if (Arrays.stream(chain).noneMatch(this::verifyContainsMojangRootPublicKey)) {
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

    private boolean validatePublicKey(JWSObject jwsToken) {
        JWSVerifier verifier;
        try {
            verifier = VERIFIER_FACTORY.createJWSVerifier(jwsToken.getHeader(), getECX509PublicKey(jwsToken.getHeader().getX509CertURL().toString()));
        } catch (JOSEException ex) {
            return false;
        }

        try {
            return jwsToken.verify(verifier);
        } catch (JOSEException e) {
            return false;
        }
    }
}
