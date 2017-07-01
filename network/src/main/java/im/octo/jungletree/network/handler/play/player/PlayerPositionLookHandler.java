package im.octo.jungletree.network.handler.play.player;

import com.flowpowered.network.MessageHandler;
import im.octo.jungletree.rainforest.minecraft.entity.Player;
import im.octo.jungletree.api.util.Location;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.message.play.player.PlayerPositionLookMessage;

public class PlayerPositionLookHandler implements MessageHandler<JSession, PlayerPositionLookMessage> {

    @Override
    public void handle(JSession session, PlayerPositionLookMessage message) {
        Player player = session.getPlayer();
        Location oldLoc = player.getLocation();
        Location newLoc = oldLoc.clone();
        message.update(newLoc);

        if (Math.abs(newLoc.getBlockX()) > 32000000 || Math.abs(newLoc.getBlockZ()) > 32000000) {
            session.getPlayer().kick("Illegal position");
            return;
        }

        /*
          don't let players move more than 100 blocks in a single packet
          if they move greater than 10 blocks, but less than 100, just warn
          this is NOT robust hack prevention - only to prevent client
          confusion about where its actual location is (e.g. during login)
        *//*
        if (message.moved()) {
            if (player.teleportedTo != null) {
                if (newLocation.equals(player.teleportedTo)) {
                    player.endTeleport();
                    return;
                } else {
                    return; // outdated location, so skip packet
                }
            } else {
                double distance = newLocation.distanceSquared(oldLocation);
                if (distance > 100 * 100) {
                    session.getPlayer().kick("You moved too quickly :( (Hacking?)");
                    return;
                } else if (distance > 100) {
                    log.warn(session.getPlayer().getName() + " moved too quickly!");
                }
            }
        }


        // call move event if movement actually occurred and there are handlers registered
        if (!oldLocation.equals(newLocation) && PlayerMoveEvent.getHandlerList().getRegisteredListeners().length > 0) {
            PlayerMoveEvent event = EventFactory.callEvent(new PlayerMoveEvent(player, oldLocation, newLocation));
            if (event.isCancelled()) {
                // tell client they're back where they started
                session.send(new PositionRotationMessage(oldLocation));
                return;
            }

            if (!event.getTo().equals(newLocation)) {
                // teleport to the set destination: fires PlayerTeleportEvent and
                // handles if the destination is in another world
                player.teleport(event.getTo(), TeleportCause.PLUGIN);
                return;
            }

            if (!Objects.equals(session.getPlayer().getLocation(), oldLocation)) {
                // plugin changed location on move event
                return;
            }
        }

        // do stuff with onGround if we need to
        if (player.isOnGround() != message.isOnGround()) {
            if (message.isOnGround() && player.getVelocity().getY() > 0) {
                // jump
                if (player.isSprinting()) {
                    player.addExhaustion(0.2f);
                } else {
                    player.addExhaustion(0.05f);
                }
            }
            player.setOnGround(message.isOnGround());
        }

        // Checks if the player is still wearing the Elytra
        ItemStack chestplate = player.getInventory().getChestplate();
        boolean hasElytra = chestplate != null && chestplate.getType() == Material.ELYTRA && chestplate.getDurability() < chestplate.getType().getMaxDurability();
        if (player.isGliding() && (player.isOnGround() || !hasElytra)) {
            player.setGliding(false);
        }

        player.addMoveExhaustion(newLocation);

        // track movement stats
        Vector delta = newLocation.clone().subtract(oldLocation).toVector();
        delta.setX(Math.abs(delta.getX()));
        delta.setY(Math.abs(delta.getY()));
        delta.setZ(Math.abs(delta.getZ()));
        int flatDistance = (int) Math.round(Math.sqrt(NumberConversions.square(delta.getX()) + NumberConversions.square(delta.getZ())) * 100.0);
        if (message.isOnGround()) {
            if (flatDistance > 0) {
                if (player.isSprinting()) {
                    player.incrementStatistic(Statistic.SPRINT_ONE_CM, flatDistance);
                } else if (player.isSneaking()) {
                    player.incrementStatistic(Statistic.CROUCH_ONE_CM, flatDistance);
                } else {
                    player.incrementStatistic(Statistic.WALK_ONE_CM, flatDistance);
                }
            }
        } else if (player.isFlying()) {
            if (flatDistance > 0) {
                player.incrementStatistic(Statistic.FLY_ONE_CM, flatDistance);
            }
        } else if (player.isInWater()) {
            if (flatDistance > 0) {
                player.incrementStatistic(Statistic.SWIM_ONE_CM, flatDistance);
            }
        }

        // move event was not fired or did nothing, simply update location
        player.setRawLocation(newLocation);*/
    }
}
