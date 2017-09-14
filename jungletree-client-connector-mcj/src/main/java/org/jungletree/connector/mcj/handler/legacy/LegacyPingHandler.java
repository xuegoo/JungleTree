package org.jungletree.connector.mcj.handler.legacy;

import com.flowpowered.network.ConnectionManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.jungletree.connector.mcj.config.JClientConnectorResourceService;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Sharable
public class LegacyPingHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(LegacyPingHandler.class);

    private final JClientConnectorResourceService resource;
    private final ConnectionManager connectionManager;

    @Inject
    public LegacyPingHandler(ClientConnectorResourceService resource, ConnectionManager connectionManager) {
        this.resource = (JClientConnectorResourceService) resource;
        this.connectionManager = connectionManager;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        ByteBuf buf = (ByteBuf) object;

        buf.markReaderIndex();
        boolean legacyPingProtocol = false;

        try {

            if (buf.readByte() == (byte) 0xFE) {
                int readable = buf.readableBytes();

                InetSocketAddress address = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();

                switch (readable) {
                    case 0: {
                        sendResponse(channelHandlerContext, writeResponse(
                                channelHandlerContext,
                                String.format("%s\u00a7%d\u00a7%d", resource.getServerDescription(), 0, resource.getMaxPlayers())
                        ));
                        break;
                    }
                    case 1: {
                        if (buf.readByte() == (byte) 0x01) {
                            sendResponse(channelHandlerContext, writeResponse(
                                    channelHandlerContext,
                                    String.format(
                                            "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                                            resource.getProtocolVersion(),
                                            resource.getGameVersion(),
                                            resource.getServerDescription(),
                                            0,
                                            resource.getMaxPlayers()
                                    )
                            ));
                        }
                        break;
                    }
                    default: {
                        if (buf.readByte() == (byte) 0x01 && buf.readByte() == (byte) 0xFA && "MC|PingHost".equals(new String(buf.readBytes(buf.readShort() * 2).array(), StandardCharsets.UTF_16BE))) {
                            int dataLength = buf.readUnsignedShort();
                            short clientVersion = buf.readUnsignedByte();
                            String hostname = buf.readBytes(buf.readShort() * 2).toString(StandardCharsets.UTF_16BE);

                            int port = buf.readInt();

                            if (clientVersion >= 73 && 7 + (hostname.length() * 2) == dataLength && buf.readableBytes() == 0) {
                                sendResponse(channelHandlerContext, writeResponse(
                                        channelHandlerContext,
                                        String.format(
                                                "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                                                resource.getProtocolVersion(),
                                                resource.getGameVersion(),
                                                resource.getServerDescription(),
                                                0,
                                                resource.getMaxPlayers()
                                        )
                                ));
                                legacyPingProtocol = true;
                            }
                        }
                        break;
                    }
                }
            }

        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } finally {
            if (!legacyPingProtocol) {
                buf.resetReaderIndex();
                channelHandlerContext.pipeline().remove("legacy_ping");
                channelHandlerContext.fireChannelRead(object);
            }
        }
    }

    private void sendResponse(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf) {
        channelhandlercontext.writeAndFlush(bytebuf).addListener(ChannelFutureListener.CLOSE);
    }

    private ByteBuf writeResponse(ChannelHandlerContext ctx, String string) {
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
