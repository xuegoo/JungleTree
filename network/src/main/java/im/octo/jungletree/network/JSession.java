package im.octo.jungletree.network;

import com.flowpowered.network.protocol.AbstractProtocol;
import com.flowpowered.network.session.BasicSession;
import io.netty.channel.Channel;

public class JSession extends BasicSession {

    public JSession(Channel channel, AbstractProtocol bootstrapProtocol) {
        super(channel, bootstrapProtocol);
    }
}
