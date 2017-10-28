package org.jungletree.clientconnector.mcb.codec.resourcepack;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.packet.Packet;
import org.jungletree.clientconnector.mcb.packet.resourcepack.ResourcePackInfoPacket;
import org.jungletree.clientconnector.mcb.resourcepack.ResourcePackInfo;

import java.util.List;

public class ResourcePackInfoCodec implements Codec<ResourcePackInfoPacket> {

    @Override
    public void encode(Packet msg, PacketBuffer buf) {
        ResourcePackInfoPacket message = (ResourcePackInfoPacket) msg;

        buf.writeBoolean(message.isMustAccept());

        List<ResourcePackInfo> behaviorPacks = message.getBehaviorPackInfo();
        buf.writeLShort((short) behaviorPacks.size());
        for (ResourcePackInfo info : behaviorPacks) {
            writeResourcePackInfo(buf, info);
        }

        List<ResourcePackInfo> resourcePacks = message.getResourcePackInfo();
        buf.writeLShort((short) resourcePacks.size());
        for (ResourcePackInfo info : resourcePacks) {
            writeResourcePackInfo(buf, info);
        }
    }

    @Override
    public ResourcePackInfoPacket decode(PacketBuffer buf) {
        ResourcePackInfoPacket message = new ResourcePackInfoPacket();

        // TODO: Decode

        return message;
    }

    private void writeResourcePackInfo(PacketBuffer buf, ResourcePackInfo info) {
        buf.writeString(info.getId());
        buf.writeString(info.getVersion());
        buf.writeLLong(info.getSize());
        buf.writeString("");
    }
}
