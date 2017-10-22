package org.jungletree.clientconnector.mcb.message.resourcepack;

import org.jungletree.clientconnector.mcb.message.Message;
import org.jungletree.clientconnector.mcb.resourcepack.ResourcePackIdVersion;

import java.util.List;
import java.util.Objects;

public class ResourcePackStackMessage implements Message {

    private byte senderSubClientId;
    private byte targetSubClientId;
    private boolean mustAccept;
    private List<ResourcePackIdVersion> behaviorPackIdVersions;
    private List<ResourcePackIdVersion> resourcePackIdVersions;

    @Override
    public byte getId() {
        return 0x07;
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

    public List<ResourcePackIdVersion> getBehaviorPackIdVersions() {
        return behaviorPackIdVersions;
    }

    public void setBehaviorPackIdVersions(List<ResourcePackIdVersion> behaviorPackIdVersions) {
        this.behaviorPackIdVersions = behaviorPackIdVersions;
    }

    public List<ResourcePackIdVersion> getResourcePackIdVersions() {
        return resourcePackIdVersions;
    }

    public void setResourcePackIdVersions(List<ResourcePackIdVersion> resourcePackIdVersions) {
        this.resourcePackIdVersions = resourcePackIdVersions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourcePackStackMessage that = (ResourcePackStackMessage) o;
        return senderSubClientId == that.senderSubClientId &&
                targetSubClientId == that.targetSubClientId &&
                mustAccept == that.mustAccept &&
                Objects.equals(behaviorPackIdVersions, that.behaviorPackIdVersions) &&
                Objects.equals(resourcePackIdVersions, that.resourcePackIdVersions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderSubClientId, targetSubClientId, mustAccept, behaviorPackIdVersions, resourcePackIdVersions);
    }

    @Override
    public String toString() {
        return "ResourcePackStackMessage{" +
                "senderSubClientId=" + senderSubClientId +
                ", targetSubClientId=" + targetSubClientId +
                ", mustAccept=" + mustAccept +
                ", behaviorPackIdVersions=" + behaviorPackIdVersions +
                ", resourcePackIdVersions=" + resourcePackIdVersions +
                '}';
    }
}
