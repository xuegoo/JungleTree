package org.jungletree.rainforest.entity;

import java.util.UUID;

public interface Player extends LivingEntity {

    @Override
    UUID getUniqueId();

    String getName();
}
