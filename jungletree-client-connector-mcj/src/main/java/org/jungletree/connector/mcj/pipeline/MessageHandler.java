package org.jungletree.connector.mcj.pipeline;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jungletree.rainforest.connector.ConnectionManager;
import org.jungletree.rainforest.connector.Message;
import org.jungletree.rainforest.connector.Session;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;

public class MessageHandler extends SimpleChannelInboundHandler<Message> {

    private final AtomicReference<Session> session;
    private final ConnectionManager connectionManager;

    @Inject
    public MessageHandler(ConnectionManager connectionManager) {
        this.session = new AtomicReference<>(null);
        this.connectionManager = connectionManager;
    }

    public Session getSession() {
        return session.get();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Channel c = ctx.channel();
        Session s = connectionManager.newSession(c);
        if (!this.session.compareAndSet(null, s)) {
            throw new IllegalStateException("Session may not be set more than once");
        }
        s.onReady();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Session session = this.session.get();
        session.onDisconnect();
        connectionManager.sessionInactivated(session);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        session.get().messageReceived(msg);
    }
}
