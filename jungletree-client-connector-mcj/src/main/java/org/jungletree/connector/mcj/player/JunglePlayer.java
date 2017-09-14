package org.jungletree.connector.mcj.player;

import org.jungletree.connector.mcj.JSession;
import org.jungletree.connector.mcj.handler.login.model.PlayerProfile;
import org.jungletree.rainforest.entity.Player;

import java.util.UUID;

public class JunglePlayer implements Player {

    private final JSession session;
    private final PlayerProfile profile;

    public JunglePlayer(JSession session, PlayerProfile profile) {
        this.session = session;
        this.profile = profile;
    }

    @Override
    public UUID getUniqueId() {
        return profile.getUniqueId();
    }

    @Override
    public String getName() {
        return profile.getName();
    }
}
