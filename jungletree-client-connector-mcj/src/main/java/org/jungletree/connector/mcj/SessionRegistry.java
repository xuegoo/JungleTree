package org.jungletree.connector.mcj;

import com.google.inject.Singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class SessionRegistry {

    private final ConcurrentMap<JSession, Boolean> sessions = new ConcurrentHashMap<>();

    public void pulse() {
        sessions.keySet().forEach(JSession::pulse);
    }

    public void add(JSession session) {
        sessions.put(session, true);
    }

    public void remove(JSession session) {
        sessions.remove(session);
    }
}
