package org.jungletree.connector.mcj.handler.login.model;

import org.jungletree.rainforest.connector.MojangUUIDParser;

import java.util.List;
import java.util.UUID;

public class AuthPlayerModel {

    private String id;
    private String name;
    private List<PlayerProperty> properties;

    public UUID getUuid() {
        return MojangUUIDParser.fromStrippedUUID(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<PlayerProperty> properties) {
        this.properties = properties;
    }
}
