package org.jungletree.clientconnector.mcb;

import io.gomint.jraknet.Connection;
import org.jungletree.clientconnector.mcb.crypto.ProtocolEncryption;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ClientConnection {

    private static final Logger log = LoggerFactory.getLogger(ClientConnection.class);

    private final BedrockServer server;
    private final ConnectivityManager connectivityManager;
    private final OutboundRequestProcessor outboundProcessor;
    private final Connection connection;
    private final ProtocolEncryption protocolEncryption;

    private volatile boolean encryptionEnabled;

    private long clientRandomId;
    private String deviceModel;
    private int deviceOS;

    private String gameVersion;
    private String languageCode;

    private int currentInputMode;
    private int defaultInputMode;

    private int guiScale;

    private String skinId;
    private String skinData;
    private String skinGeometryName;

    private int uiProfile;

    public ClientConnection(BedrockServer server, Connection connection) {
        this.server = server;
        this.connectivityManager = server.getConnectivityManager();
        this.outboundProcessor = new OutboundRequestProcessor(connectivityManager, this);
        this.connection = connection;
        this.protocolEncryption = new ProtocolEncryption(server.getServerKeyPair());
    }

    public void flush() {
        outboundProcessor.flush();
    }

    public BedrockServer getServer() {
        return server;
    }

    public Connection getConnection() {
        return connection;
    }

    public ProtocolEncryption getProtocolEncryption() {
        return protocolEncryption;
    }

    public boolean isEncryptionEnabled() {
        return encryptionEnabled;
    }

    public void setEncryptionEnabled(boolean encryptionEnabled) {
        this.encryptionEnabled = encryptionEnabled;
    }

    public void send(Packet packet) {
        this.outboundProcessor.batch(packet);
    }

    public long getClientRandomId() {
        return clientRandomId;
    }

    public void setClientRandomId(long clientRandomId) {
        this.clientRandomId = clientRandomId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public int getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(int deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getCurrentInputMode() {
        return currentInputMode;
    }

    public void setCurrentInputMode(int currentInputMode) {
        this.currentInputMode = currentInputMode;
    }

    public int getDefaultInputMode() {
        return defaultInputMode;
    }

    public void setDefaultInputMode(int defaultInputMode) {
        this.defaultInputMode = defaultInputMode;
    }

    public int getGuiScale() {
        return guiScale;
    }

    public void setGuiScale(int guiScale) {
        this.guiScale = guiScale;
    }

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String skinId) {
        this.skinId = skinId;
    }

    public String getSkinData() {
        return skinData;
    }

    public void setSkinData(String skinData) {
        this.skinData = skinData;
    }

    public String getSkinGeometryName() {
        return skinGeometryName;
    }

    public void setSkinGeometryName(String skinGeometryName) {
        this.skinGeometryName = skinGeometryName;
    }

    public int getUiProfile() {
        return uiProfile;
    }

    public void setUiProfile(int uiProfile) {
        this.uiProfile = uiProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientConnection that = (ClientConnection) o;
        return clientRandomId == that.clientRandomId &&
                deviceOS == that.deviceOS &&
                currentInputMode == that.currentInputMode &&
                defaultInputMode == that.defaultInputMode &&
                guiScale == that.guiScale &&
                uiProfile == that.uiProfile &&
                Objects.equals(server, that.server) &&
                Objects.equals(connectivityManager, that.connectivityManager) &&
                Objects.equals(outboundProcessor, that.outboundProcessor) &&
                Objects.equals(connection, that.connection) &&
                Objects.equals(deviceModel, that.deviceModel) &&
                Objects.equals(gameVersion, that.gameVersion) &&
                Objects.equals(languageCode, that.languageCode) &&
                Objects.equals(skinId, that.skinId) &&
                Objects.equals(skinData, that.skinData) &&
                Objects.equals(skinGeometryName, that.skinGeometryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, connectivityManager, outboundProcessor, connection, clientRandomId, deviceModel, deviceOS, gameVersion, languageCode, currentInputMode, defaultInputMode, guiScale, skinId, skinData, skinGeometryName, uiProfile);
    }

    @Override
    public String toString() {
        return "ClientConnection{" +
                "server=" + server +
                ", connectivityManager=" + connectivityManager +
                ", outboundProcessor=" + outboundProcessor +
                ", connection=" + connection +
                ", clientRandomId=" + clientRandomId +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceOS=" + deviceOS +
                ", gameVersion='" + gameVersion + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", currentInputMode=" + currentInputMode +
                ", defaultInputMode=" + defaultInputMode +
                ", guiScale=" + guiScale +
                ", skinId='" + skinId + '\'' +
                ", skinData='" + skinData + '\'' +
                ", skinGeometryName='" + skinGeometryName + '\'' +
                ", uiProfile=" + uiProfile +
                '}';
    }
}
