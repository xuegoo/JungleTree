package org.jungletree.clientconnector.mcb.packet;

import java.util.Objects;

public class DisconnectPacket extends SubClientPacket {

    private boolean hideScreen;
    private String reason;

    @Override
    public byte getId() {
        return 0x05;
    }

    public boolean isHideScreen() {
        return hideScreen;
    }

    public void setHideScreen(boolean hideScreen) {
        this.hideScreen = hideScreen;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DisconnectPacket packet = (DisconnectPacket) o;
        return hideScreen == packet.hideScreen &&
                Objects.equals(reason, packet.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hideScreen, reason);
    }

    @Override
    public String toString() {
        return "DisconnectPacket{" +
                "hideScreen=" + hideScreen +
                ", reason='" + reason + '\'' +
                "} " + super.toString();
    }
}
