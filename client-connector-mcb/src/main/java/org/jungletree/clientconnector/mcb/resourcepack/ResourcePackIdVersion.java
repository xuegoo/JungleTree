package org.jungletree.clientconnector.mcb.resourcepack;

import java.util.Objects;

public class ResourcePackIdVersion {

    private String id;
    private String version;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourcePackIdVersion that = (ResourcePackIdVersion) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public String toString() {
        return "ResourcePackIdVersion{" +
                "id='" + id + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
