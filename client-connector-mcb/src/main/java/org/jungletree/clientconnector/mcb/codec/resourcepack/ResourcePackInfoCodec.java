package org.jungletree.clientconnector.mcb.codec.resourcepack;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.message.Message;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackInfoMessage;
import org.jungletree.clientconnector.mcb.resourcepack.ResourcePackInfo;

import java.util.List;

public class ResourcePackInfoCodec implements Codec<ResourcePackInfoMessage> {

    @Override
    public void encode(Message msg, PacketBuffer buf) {
        ResourcePackInfoMessage message = (ResourcePackInfoMessage) msg;

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
    public ResourcePackInfoMessage decode(PacketBuffer buf) {
        ResourcePackInfoMessage message = new ResourcePackInfoMessage();

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
