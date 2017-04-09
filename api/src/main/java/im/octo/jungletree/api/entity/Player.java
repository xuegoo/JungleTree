package im.octo.jungletree.api.entity;

import im.octo.jungletree.api.player.OnlinePlayer;
import im.octo.jungletree.api.world.Location;

import java.util.Collection;

public interface Player extends OnlinePlayer, LivingEntity {

    void setFlying(boolean flying);

    boolean isFlyingAllowed();

    void setCompassTarget(Location location);

    Location getLocation();

    void kick(String reason);

    boolean isGrounded();

    Collection<String> getListeningChannels();

    void addChannel(String channel);

    void removeChannel(String channel);

    void chat(String message);
}
