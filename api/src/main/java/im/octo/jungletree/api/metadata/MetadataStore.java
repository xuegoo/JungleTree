package im.octo.jungletree.api.metadata;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MetadataStore<T extends Serializable> implements Serializable {

    private final Map<String, T> data = new ConcurrentHashMap<>();

    public T put(String key, T object) {
        return data.put(key, object);
    }

    public T remove(String key) {
        return data.remove(key);
    }

    public T get(String key) {
        return data.get(key);
    }

    public T getOrDefault(String key, T defaultValue) {
        return data.getOrDefault(key, defaultValue);
    }

    public Map<String, T> getContents() {
        return Collections.unmodifiableMap(data);
    }

    public void setContents(Map<String, T> newData) {
        data.clear();
        data.putAll(newData);
    }
}
