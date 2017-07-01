package im.octo.jungletree.network.pipeline;

import com.flowpowered.network.ConnectionManager;
import com.flowpowered.network.Message;
import com.flowpowered.network.session.Session;
import im.octo.jungletree.network.JSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Experimental pipeline component, based on flow-net's MessageHandler.
 */
public final class MessageHandler extends SimpleChannelInboundHandler<Message> {

    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);

    /**
     * The associated session
     */
    private final AtomicReference<Session> session = new AtomicReference<>(null);
    private final ConnectionManager connectionManager;

    /**
     * Creates a new network event handler.
     *
     * @param connectionManager The connection manager to manage connections for this message handler.
     */
    public MessageHandler(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel c = ctx.channel();
        Session s = connectionManager.newSession(c);
        if (!session.compareAndSet(null, s)) {
            throw new IllegalStateException("Session may not be set more than once");
        }
        s.onReady();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Session session = this.session.get();
        session.onDisconnect();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message i) {
        session.get().messageReceived(i);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            Session session = this.session.get();
            if (session instanceof JSession) {
                JSession jSession = (JSession)session;
                jSession.idle(); // todo: find a more elegant way to do this in the future
            } else {
                throw new RuntimeException("Session is not instance of JSession. You must be using a custom implementation.");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        session.get().onInboundThrowable(cause);
    }

    public AtomicReference<Session> getSession() {
        return session;
    }
}
