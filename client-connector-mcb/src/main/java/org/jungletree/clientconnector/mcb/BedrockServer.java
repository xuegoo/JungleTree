package org.jungletree.clientconnector.mcb;

import io.gomint.jraknet.ServerSocket;
import io.gomint.jraknet.Socket;
import io.gomint.jraknet.SocketEvent;
import io.gomint.jraknet.SocketEventHandler;
import org.jungletree.rainforest.auth.messages.GetServerTokenMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.auth.messages.JwtAuthRequestMessage;
import org.jungletree.rainforest.messaging.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BedrockServer {

    private static final Logger log = LoggerFactory.getLogger(BedrockServer.class);

    private static BedrockServer INSTANCE;

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

    public BedrockServer() {
        INSTANCE = this;

        this.messaging = ServiceLoader.load(MessagingService.class).findFirst().orElseThrow(NoSuchElementException::new);
        messaging.start();
        messaging.registerMessage(JwtAuthRequestMessage.class);
        messaging.registerMessage(JwtAuthReponseMessage.class);
        messaging.registerMessage(GetServerTokenMessage.class);

        this.connectivityManager = new ConnectivityManager(this);
    }

    public static BedrockServer getServer() {
        return INSTANCE;
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
}
