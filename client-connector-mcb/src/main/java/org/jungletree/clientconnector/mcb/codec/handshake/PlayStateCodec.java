package org.jungletree.clientconnector.mcb.codec.handshake;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.PlayState;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.jungletree.clientconnector.mcb.packet.handshake.PlayStatePacket;

public class PlayStateCodec implements Codec<PlayStatePacket> {

    @Override
    public void encode(Packet msg, PacketBuffer buf) {
        PlayStatePacket message = (PlayStatePacket) msg;
        buf.writeInt(message.getPlayState().getOrdinal());
    }

    @Override
    public PlayStatePacket decode(PacketBuffer buf) {
        PlayStatePacket message = new PlayStatePacket();
        message.setPlayState(PlayState.fromOrdinal(buf.readInt()));
        return message;
    }
}
