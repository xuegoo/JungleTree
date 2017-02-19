package im.octo.jungletree.network;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.network.NetworkServer;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

@Singleton
public class JNetworkServer implements NetworkServer {

    private final Server server;
    private CountDownLatch latch;

    @Inject
    public JNetworkServer(CountDownLatch latch) {
        this.server = Rainforest.getServer();
        this.latch = latch;
    }

    public ChannelFuture bind(InetSocketAddress address) {
        // TODO: Do something
        return null;
    }

    public void onBindSuccess(InetSocketAddress address) {
        latch.countDown();
    }

    public void onBindFailure(InetSocketAddress address, Throwable t) {
        // TODO: Do something
    }

    public void shutdown() {
        // TODO: Do something
    }
}
