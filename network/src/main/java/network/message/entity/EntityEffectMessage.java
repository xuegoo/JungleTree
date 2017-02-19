package network.message.entity;

import com.flowpowered.network.Message;

public class EntityEffectMessage implements Message {

    private final int id;
    private final int effect;
    private final int amplifier;
    private final int duration;
    private final int hideParticles;

    public EntityEffectMessage(int id, int effect, int amplifier, int duration, int hideParticles) {
        this.id = id;
        this.effect = effect;
        this.amplifier = amplifier;
        this.duration = duration;
        this.hideParticles = hideParticles;
    }

    public int getId() {
        return id;
    }

    public int getEffect() {
        return effect;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public int getHideParticles() {
        return hideParticles;
    }
}
