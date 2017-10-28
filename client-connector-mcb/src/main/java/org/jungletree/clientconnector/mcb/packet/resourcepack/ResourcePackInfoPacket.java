package org.jungletree.clientconnector.mcb.packet.resourcepack;

import org.jungletree.clientconnector.mcb.packet.Packet;
import org.jungletree.clientconnector.mcb.resourcepack.ResourcePackInfo;

import java.util.List;
import java.util.Objects;

public class ResourcePackInfoPacket implements Packet {

    private byte senderSubClientId;
    private byte targetSubClientId;
    private boolean mustAccept;
    private List<ResourcePackInfo> behaviorPackInfo;
    private List<ResourcePackInfo> resourcePackInfo;

    @Override
    public byte getId() {
        return 0x06;
    }

    @Override
    public byte getSenderSubClientId() {
        return senderSubClientId;
    }

    @Override
    public void setSenderSubClientId(byte senderSubClientId) {
        this.senderSubClientId = senderSubClientId;
    }

    @Override
    public byte getTargetSubClientId() {
        return targetSubClientId;
    }

    @Override
    public void setTargetSubClientId(byte targetSubClientId) {
        this.targetSubClientId = targetSubClientId;
    }

    public boolean isMustAccept() {
        return mustAccept;
    }

    public void setMustAccept(boolean mustAccept) {
        this.mustAccept = mustAccept;
    }

    public List<ResourcePackInfo> getBehaviorPackInfo() {
        return behaviorPackInfo;
    }

    public void setBehaviorPackInfo(List<ResourcePackInfo> behaviorPackInfo) {
        this.behaviorPackInfo = behaviorPackInfo;
    }

    public List<ResourcePackInfo> getResourcePackInfo() {
        return resourcePackInfo;
    }

    public void setResourcePackInfo(List<ResourcePackInfo> resourcePackInfo) {
        this.resourcePackInfo = resourcePackInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourcePackInfoPacket that = (ResourcePackInfoPacket) o;
        return senderSubClientId == that.senderSubClientId &&
                targetSubClientId == that.targetSubClientId &&
                mustAccept == that.mustAccept &&
                Objects.equals(behaviorPackInfo, that.behaviorPackInfo) &&
                Objects.equals(resourcePackInfo, that.resourcePackInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderSubClientId, targetSubClientId, mustAccept, behaviorPackInfo, resourcePackInfo);
    }

    @Override
    public String toString() {
        return "ResourcePackInfoPacket{" +
                "senderSubClientId=" + senderSubClientId +
                ", targetSubClientId=" + targetSubClientId +
                ", mustAccept=" + mustAccept +
                ", behaviorPackInfo=" + behaviorPackInfo +
                ", resourcePackInfo=" + resourcePackInfo +
                '}';
    }
}
