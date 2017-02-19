package im.octo.jungletree.network;

import com.flowpowered.network.protocol.AbstractProtocol;
import com.flowpowered.network.session.BasicSession;
import im.octo.jungletree.api.entity.Player;
import im.octo.jungletree.api.player.meta.PlayerProfile;
import io.netty.channel.Channel;

import javax.crypto.SecretKey;

public class JSession extends BasicSession {

    private Player player;

    public JSession(Channel channel, AbstractProtocol bootstrapProtocol) {
        super(channel, bootstrapProtocol);
    }

    public void disconnect(String reason) {
        disconnect(reason, false);
    }

    public void disconnect(String reason, boolean overrideKick) {
        if (player != null && !overrideKick) {
            // TODO: Implement
        }
    }

    public byte[] getVerifyToken() {
        return null;
    }

    public void enableEncryption(SecretKey sharedSecret) {
        // TODO: Implement
    }

    public String getVerifyUsername() {
        // TODO: Implement
        return null;
    }

    public void setPlayer(PlayerProfile playerProfile) {
        // TODO: Implement
    }
}
