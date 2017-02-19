package im.octo.jungletree.api.entity;

import java.util.Optional;

// TODO: Upgrade magic numbers to named strings
public enum EntityEffect {

    MYSTERY_LIVING(0),
    MYSTERY_PLAYER(1),
    HURT(2),
    DEATH(3),
    IRON_GOLEM_FLING_ARMS(4),
    // 5
    WOLF_SMOKE(6),
    WOLF_HEARTS(7),
    WOLF_SHAKE(8),
    EATING_ACCEPTED(9),
    SHEEP_EAT(10),
    IRON_GOLEM_ROSE(11),
    VILLAGER_HEART(12),
    VILLAGER_ANGRY(13),
    VILLAGER_HAPPY(14),
    WITCH_MAGIC(15),
    ZOMBIE_TRANSFORM(16),
    FIREWORK_EXPLODE(17),
    ANIMAL_HEARTS(18),
    // 19
    // 20
    // 21
    ENABLE_REDUCED_DEBUG_INFO(22),
    DISABLE_REDUCED_DEBUG_INFO(23),
    OP_LEVEL_0(24),
    OP_LEVEL_1(25),
    OP_LEVEL_2(26),
    OP_LEVEL_3(27),
    OP_LEVEL_4(28);

    private final byte id;

    EntityEffect(int id) {
        this.id = (byte) id;
    }

    public int getId() {
        return id;
    }

    public static Optional<EntityEffect> fromId(int id) {
        for (EntityEffect value : values()) {
            if (id == value.id) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
