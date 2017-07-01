package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;
import im.octo.jungletree.api.exception.InvalidPacketException;

import java.util.Optional;

public class ClientStatusMessage implements Message {

    private final ClientAction action;

    public ClientStatusMessage(int actionId) {
        Optional<ClientAction> actionOptional = ClientAction.fromId(actionId);
        if (actionOptional.isPresent()) {
            action = actionOptional.get();
        } else {
            throw new InvalidPacketException();
        }
    }

    public ClientAction getAction() {
        return action;
    }

    public int getActionId() {
        return action.getActionId();
    }
}
