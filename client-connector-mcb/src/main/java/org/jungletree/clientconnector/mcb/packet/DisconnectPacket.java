package org.jungletree.clientconnector.mcb.packet;

import java.util.Objects;

public class DisconnectPacket extends SubClientPacket {

    private String reason;

    @Override
    public byte getId() {
        return 0x05;
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
        DisconnectPacket that = (DisconnectPacket) o;
        return Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reason);
    }

    @Override
    public String toString() {
        return "DisconnectPacket{" +
                "reason='" + reason + '\'' +
                "} " + super.toString();
    }
}
