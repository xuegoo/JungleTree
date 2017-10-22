package org.jungletree.clientconnector.mcb.codec.resourcepack;

import io.gomint.jraknet.PacketBuffer;
import org.jungletree.clientconnector.mcb.codec.Codec;
import org.jungletree.clientconnector.mcb.message.Message;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackStackMessage;
import org.jungletree.clientconnector.mcb.resourcepack.ResourcePackIdVersion;

import java.util.List;

public class ResourcePackStackCodec implements Codec<ResourcePackStackMessage> {

    @Override
    public void encode(Message msg, PacketBuffer buf) {
        ResourcePackStackMessage message = (ResourcePackStackMessage) msg;

        buf.writeBoolean(message.isMustAccept());

        List<ResourcePackIdVersion> behaviorPacks = message.getBehaviorPackIdVersions();
        buf.writeLShort((short) behaviorPacks.size());
        for (ResourcePackIdVersion info : behaviorPacks) {
            writeResourcePackInfo(buf, info);
        }

        List<ResourcePackIdVersion> resourcePacks = message.getResourcePackIdVersions();
        buf.writeLShort((short) resourcePacks.size());
        for (ResourcePackIdVersion info : resourcePacks) {
            writeResourcePackInfo(buf, info);
        }
    }

    @Override
    public ResourcePackStackMessage decode(PacketBuffer buf) {
        ResourcePackStackMessage message = new ResourcePackStackMessage();

        // TODO: Decode

        return message;
    }

    private void writeResourcePackInfo(PacketBuffer buf, ResourcePackIdVersion info) {
        buf.writeString(info.getId());
        buf.writeString(info.getVersion());
    }
}
