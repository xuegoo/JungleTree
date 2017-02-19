package network.protocol;

import network.codec.status.StatusPingCodec;
import network.codec.status.StatusRequestCodec;
import network.codec.status.StatusResponseCodec;
import network.handler.status.StatusPingHandler;
import network.handler.status.StatusRequestHandler;
import network.message.status.StatusPingMessage;
import network.message.status.StatusRequestMessage;
import network.message.status.StatusResponseMessage;

public class StatusProtocol extends JProtocol {

    public StatusProtocol() {
        super(ProtocolType.STATUS.name(), 2);
        inbound(0x00, StatusRequestMessage.class, StatusRequestCodec.class, StatusRequestHandler.class);
        inbound(0x01, StatusPingMessage.class, StatusPingCodec.class, StatusPingHandler.class);

        outbound(0x00, StatusResponseMessage.class, StatusResponseCodec.class);
        outbound(0x01, StatusPingMessage.class, StatusPingCodec.class);
    }
}
