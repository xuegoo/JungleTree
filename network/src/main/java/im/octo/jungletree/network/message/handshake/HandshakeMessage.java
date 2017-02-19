package im.octo.jungletree.network.message.handshake;

import com.flowpowered.network.AsyncableMessage;

public class HandshakeMessage implements AsyncableMessage {

    private final int protocolVersion;
    private final String address;
    private final int port;
    private final int state;

    public HandshakeMessage(int protocolVersion, String address, int port, int state) {
        this.protocolVersion = protocolVersion;
        this.address = address;
        this.port = port;
        this.state = state;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getState() {
        return state;
    }
}
