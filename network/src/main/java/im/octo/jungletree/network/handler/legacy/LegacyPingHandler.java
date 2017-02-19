package im.octo.jungletree.network.handler.legacy;

import com.flowpowered.network.ConnectionManager;
import com.google.common.base.Charsets;
import com.google.inject.Injector;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.type.server.ServerListPingEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

public class LegacyPingHandler extends ChannelInboundHandlerAdapter {

    ConnectionManager connectionManager;

    public LegacyPingHandler(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        ByteBuf bytebuf = (ByteBuf) object;

        bytebuf.markReaderIndex();
        boolean legacyPingProtocol = false;

        try {
            if (bytebuf.readByte() == (byte) 0xFE) {
                Server server = Rainforest.getServer();
                int readableBytes = bytebuf.readableBytes();

                InetSocketAddress inetsocketaddress = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();

                ServerListPingEvent event = new ServerListPingEvent(inetsocketaddress.getAddress(), server.getDescription(), server.getOnlinePlayers().size(), server.getMaxOnlinePlayers());
                Injector guice = server.getGuice();
                EventService eventService = guice.getInstance(EventService.class);
                eventService.call(event);

                switch (readableBytes) {
                    case 0:
                        sendByteBuf(channelHandlerContext, responseToByteBuf(channelHandlerContext, String.format("%s\u00a7%d\u00a7%d", event.getDescription(), event.getPlayerCount(), event.getMaxPlayers())));
                        legacyPingProtocol = true;
                        break;
                    case 1:
                        if (bytebuf.readByte() == (byte) 0x01) {
                            sendByteBuf(channelHandlerContext, responseToByteBuf(channelHandlerContext, String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", server.getGameVersion().getProtocolVersion(), server.getGameVersion().getName(), event.getDescription(), event.getPlayerCount(), event.getMaxPlayers())));
                            legacyPingProtocol = true;
                        }
                        break;
                    default:
                        if (bytebuf.readByte() == (byte) 0x01 && bytebuf.readByte() == (byte) 0xFA && "MC|PingHost".equals(new String(bytebuf.readBytes(bytebuf.readShort() * 2).array(), Charsets.UTF_16BE))) {
                            int dataLength = bytebuf.readUnsignedShort();
                            short clientVersion = bytebuf.readUnsignedByte();
                            String hostname = bytebuf.readBytes(bytebuf.readShort() * 2).toString(Charsets.UTF_16BE);
                            int port = bytebuf.readInt();

                            if (clientVersion >= 73 && 7 + (hostname.length() * 2) == dataLength && bytebuf.readableBytes() == 0) {
                                sendByteBuf(channelHandlerContext, responseToByteBuf(channelHandlerContext, String.format("\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", server.getGameVersion().getProtocolVersion(), server.getGameVersion().getName(), event.getDescription(), event.getPlayerCount(), event.getMaxPlayers())));
                                legacyPingProtocol = true;
                            }
                        }
                        break;
                }
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            // Silently catch the exception
        } finally {
            // check if not successful, otherwise the connection has already been closed
            if (!legacyPingProtocol) {
                bytebuf.resetReaderIndex();
                channelHandlerContext.pipeline().remove("legacy_ping");
                channelHandlerContext.fireChannelRead(object);
            }
        }
    }

    private void sendByteBuf(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf) {
        channelhandlercontext.writeAndFlush(bytebuf).addListener(ChannelFutureListener.CLOSE);
    }

    private ByteBuf responseToByteBuf(ChannelHandlerContext ctx, String string) {
        ByteBuf bytebuf = ctx.alloc().buffer(3 + string.length());

        bytebuf.writeByte(0xFF);
        bytebuf.writeShort(string.length());
        try {
            bytebuf.writeBytes(string.getBytes("UTF-16BE"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bytebuf;
    }
}
