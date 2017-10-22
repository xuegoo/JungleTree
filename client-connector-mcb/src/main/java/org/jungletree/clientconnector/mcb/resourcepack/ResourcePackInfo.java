package org.jungletree.clientconnector.mcb.resourcepack;

import java.util.Objects;

public class ResourcePackInfo {

    private String id;
    private String version;
    private long size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourcePackInfo that = (ResourcePackInfo) o;
        return size == that.size &&
                Objects.equals(id, that.id) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, size);
    }

    @Override
    public String toString() {
        return "ResourcePackInfo{" +
                "id='" + id + '\'' +
                ", version='" + version + '\'' +
                ", size=" + size +
                '}';
    }
}
