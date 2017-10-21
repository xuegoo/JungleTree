package org.jungletree.auth;

import java.util.Objects;

public class JwtPayload {

    private String capeData;
    private long clientRandomId;
    private int currentInputMode;
    private int defaultInputMode;
    private String deviceModel;
    private int deviceOS;
    private String gameVersion;
    private int guiScale;
    private String languageCode;
    private String serverAddress;
    private String skinData;
    private String skinGeometryName;
    private String skinId;
    private int uiProfile;

    public String getCapeData() {
        return capeData;
    }

    public void setCapeData(String capeData) {
        this.capeData = capeData;
    }

    public long getClientRandomId() {
        return clientRandomId;
    }

    public void setClientRandomId(long clientRandomId) {
        this.clientRandomId = clientRandomId;
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

    public int getGuiScale() {
        return guiScale;
    }

    public void setGuiScale(int guiScale) {
        this.guiScale = guiScale;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
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

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String skinId) {
        this.skinId = skinId;
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
        JwtPayload payload = (JwtPayload) o;
        return clientRandomId == payload.clientRandomId &&
                currentInputMode == payload.currentInputMode &&
                defaultInputMode == payload.defaultInputMode &&
                deviceOS == payload.deviceOS &&
                guiScale == payload.guiScale &&
                uiProfile == payload.uiProfile &&
                Objects.equals(capeData, payload.capeData) &&
                Objects.equals(deviceModel, payload.deviceModel) &&
                Objects.equals(gameVersion, payload.gameVersion) &&
                Objects.equals(languageCode, payload.languageCode) &&
                Objects.equals(serverAddress, payload.serverAddress) &&
                Objects.equals(skinData, payload.skinData) &&
                Objects.equals(skinGeometryName, payload.skinGeometryName) &&
                Objects.equals(skinId, payload.skinId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capeData, clientRandomId, currentInputMode, defaultInputMode, deviceModel, deviceOS, gameVersion, guiScale, languageCode, serverAddress, skinData, skinGeometryName, skinId, uiProfile);
    }

    @Override
    public String toString() {
        return "JwtPayload{" +
                "capeData='" + capeData + '\'' +
                ", clientRandomId=" + clientRandomId +
                ", currentInputMode=" + currentInputMode +
                ", defaultInputMode=" + defaultInputMode +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceOS=" + deviceOS +
                ", gameVersion='" + gameVersion + '\'' +
                ", guiScale=" + guiScale +
                ", languageCode='" + languageCode + '\'' +
                ", serverAddress='" + serverAddress + '\'' +
                ", skinData='" + skinData + '\'' +
                ", skinGeometryName='" + skinGeometryName + '\'' +
                ", skinId='" + skinId + '\'' +
                ", uiProfile=" + uiProfile +
                '}';
    }
}
