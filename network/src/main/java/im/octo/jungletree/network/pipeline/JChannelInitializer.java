package im.octo.jungletree.network.pipeline;

import com.flowpowered.network.ConnectionManager;
import im.octo.jungletree.network.handler.legacy.LegacyPingHandler;
import im.octo.jungletree.network.protocol.ProtocolType;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger log = LoggerFactory.getLogger(JChannelInitializer.class);

    private static final int READ_TIMEOUT = 20;
    private static final int WRITE_IDLE_TIMEOUT = 15;

    private final ConnectionManager connectionManager;

    public JChannelInitializer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    protected void initChannel(SocketChannel c) throws Exception {
        MessageHandler handler = new MessageHandler(connectionManager);
        CodecsHandler codecs = new CodecsHandler(ProtocolType.HANDSHAKE.getProtocol());
        FramingHandler framing = new FramingHandler();

        try {
            c.config().setOption(ChannelOption.IP_TOS, 0x18);
        } catch (ChannelException ex) {
            // Not supported on all OSs, like Windows XP and lesser
            log.error("Your OS does not support type of service.");
        }
        c.config().setAllocator(PooledByteBufAllocator.DEFAULT);

        c.pipeline()
                .addLast("legacy_ping", new LegacyPingHandler(connectionManager))
                .addLast("encryption", NoopHandler.INSTANCE)
                .addLast("framing", framing)
                .addLast("compression", NoopHandler.INSTANCE)
                .addLast("codecs", codecs)
                .addLast("readtimeout", new ReadTimeoutHandler(READ_TIMEOUT))
                .addLast("writeidletimeout", new IdleStateHandler(0, WRITE_IDLE_TIMEOUT, 0))
                .addLast("handler", handler);
    }
}
