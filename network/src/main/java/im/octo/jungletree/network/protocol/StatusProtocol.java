package im.octo.jungletree.network.protocol;

import im.octo.jungletree.network.codec.status.StatusPingCodec;
import im.octo.jungletree.network.codec.status.StatusRequestCodec;
import im.octo.jungletree.network.codec.status.StatusResponseCodec;
import im.octo.jungletree.network.handler.status.StatusRequestHandler;
import im.octo.jungletree.network.message.status.StatusResponseMessage;
import im.octo.jungletree.network.handler.status.StatusPingHandler;
import im.octo.jungletree.network.message.status.StatusPingMessage;
import im.octo.jungletree.network.message.status.StatusRequestMessage;

public class StatusProtocol extends JProtocol {

    public StatusProtocol() {
        super(ProtocolType.STATUS.name(), 2);
        inbound(0x00, StatusRequestMessage.class, StatusRequestCodec.class, StatusRequestHandler.class);
        inbound(0x01, StatusPingMessage.class, StatusPingCodec.class, StatusPingHandler.class);

        outbound(0x00, StatusResponseMessage.class, StatusResponseCodec.class);
        outbound(0x01, StatusPingMessage.class, StatusPingCodec.class);
    }
}
