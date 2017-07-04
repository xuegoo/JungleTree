package org.jungletree.rainforest.plugin;

public interface Plugin {

    String getName();

    String getVersion();

    void onLoad();

    void onUnload();
}
