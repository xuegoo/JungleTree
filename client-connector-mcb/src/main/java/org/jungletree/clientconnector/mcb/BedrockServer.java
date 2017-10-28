package org.jungletree.clientconnector.mcb;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import io.gomint.jraknet.ServerSocket;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jungletree.clientconnector.mcb.crypto.ClientSaltTokenFactory;
import org.jungletree.rainforest.auth.messages.GetServerTokenMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthRequestMessage;
import org.jungletree.rainforest.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.net.URI;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BedrockServer {

    private static final Logger log = LoggerFactory.getLogger(BedrockServer.class);

    private final Map<SocketEvent.Type, SocketEventHandler> handlers = new ConcurrentHashMap<>();

    private final Map<Socket, ClientConnection> connections = new ConcurrentHashMap<>();
    private final ConnectivityManager connectivityManager;

    private String host;
    private int port;

    private final UUID serverUuid = UUID.randomUUID();

    private String motd;
    private int protocolVersion;
    private String protocolVersionName;
    private int maxPlayers;

    private volatile int onlinePlayers;

    private ServerSocket server;

    private final MessagingService messaging;

    private final KeyPair serverKeyPair;
    private final ClientSaltTokenFactory clientSaltTokenFactory;

    public BedrockServer() {
        Security.addProvider(new BouncyCastleProvider());

        this.messaging = ServiceLoader.load(MessagingService.class).findFirst().orElseThrow(NoSuchElementException::new);
        messaging.start();
        messaging.registerMessage(JwtAuthRequestMessage.class);
        messaging.registerMessage(JwtAuthReponseMessage.class);
        messaging.registerMessage(GetServerTokenMessage.class);

        log.info("Generating server key pair");
        this.serverKeyPair = generateKeyPair();
        this.clientSaltTokenFactory = new ClientSaltTokenFactory(serverKeyPair, createServerTokenHeader((ECPublicKey) serverKeyPair.getPublic()));

        this.connectivityManager = new ConnectivityManager(this);
    }

    public KeyPair getServerKeyPair() {
        return serverKeyPair;
    }

    public ClientSaltTokenFactory getClientSaltTokenFactory() {
        return clientSaltTokenFactory;
    }

    public void registerSocketEventHandler(SocketEvent.Type type, SocketEventHandler handler) {
        handlers.put(type, handler);
    }

    public MessagingService getMessagingService() {
        return messaging;
    }

    public ConnectivityManager getConnectivityManager() {
        return connectivityManager;
    }

    public Map<Socket, ClientConnection> getConnections() {
        return Collections.unmodifiableMap(connections);
    }

    public void connectClient(Socket socket, ClientConnection clientConnection) {
        this.connections.put(socket, clientConnection);
    }

    public void disconnectClient(Socket socket) {
        this.connections.remove(socket);
    }

    public void disconnectClient(ClientConnection connection, String reason) {
        connection.getConnection().disconnect(reason);
        this.connections.entrySet().stream()
                .filter(e -> e.getValue().equals(connection))
                .forEach(e -> connections.remove(e.getKey()));
    }

    public UUID getServerUuid() {
        return serverUuid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getProtocolVersionName() {
        return protocolVersionName;
    }

    public void setProtocolVersionName(String protocolVersionName) {
        this.protocolVersionName = protocolVersionName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public void start() {
        log.info("Starting server");

        this.connectivityManager.start();
        Runtime.getRuntime().addShutdownHook(new Thread(connectivityManager::stop));

        this.server = new ServerSocket(maxPlayers);
        server.setMojangModificationEnabled(true);
        server.setEventHandler((socket, socketEvent) -> handlers.entrySet().stream()
                .filter(e -> socketEvent.getType().equals(e.getKey()))
                .forEach(e -> e.getValue().onSocketEvent(socket, socketEvent)));

        try {
            server.bind(getHost(), getPort());
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    private KeyPair generateKeyPair()  {
        try {
            // Java public key default is X509, and private key defaults to PKCS#8
            KeyPairGenerator generator = KeyPairGenerator.getInstance("EC", "BC");
            generator.initialize(384, new SecureRandom());
            return generator.generateKeyPair();
        } catch (NoSuchProviderException | NoSuchAlgorithmException ex) {
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
}
