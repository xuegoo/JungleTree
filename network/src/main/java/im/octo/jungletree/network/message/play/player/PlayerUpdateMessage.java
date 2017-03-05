package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.world.Location;

public class PlayerUpdateMessage implements Message {

    private final boolean grounded;

    public PlayerUpdateMessage(boolean grounded) {
        this.grounded = grounded;
    }

    public void update(Location location) {
        // do nothing
    }

    public boolean moved() {
        return false;
    }
}
