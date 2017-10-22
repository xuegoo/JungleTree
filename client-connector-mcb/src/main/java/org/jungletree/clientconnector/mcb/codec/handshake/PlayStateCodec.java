package org.jungletree.clientconnector.mcb.codec.handshake;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.PlayState;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.message.Message;
import org.jungletree.clientconnector.mcb.message.handshake.PlayStateMessage;

public class PlayStateCodec implements Codec<PlayStateMessage> {

    @Override
    public void encode(Message msg, PacketBuffer buf) {
        PlayStateMessage message = (PlayStateMessage) msg;
        buf.writeInt(message.getPlayState().getOrdinal());
    }

    @Override
    public PlayStateMessage decode(PacketBuffer buf) {
        PlayStateMessage message = new PlayStateMessage();
        message.setPlayState(PlayState.fromOrdinal(buf.readInt()));
        return message;
    }
}
