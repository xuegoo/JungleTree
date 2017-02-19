package im.octo.jungletree.network.handler.login;

import com.flowpowered.network.MessageHandler;
import com.google.inject.Injector;
import im.octo.jungletree.api.Rainforest;
import im.octo.jungletree.api.Server;
import im.octo.jungletree.api.event.EventService;
import im.octo.jungletree.api.event.type.AsyncPlayerPreLoginEvent;
import im.octo.jungletree.api.network.LoginResult;
import im.octo.jungletree.api.player.meta.PlayerProfile;
import im.octo.jungletree.api.scheduler.TaskScheduler;
import im.octo.jungletree.network.JSession;
import im.octo.jungletree.network.ProxyData;
import im.octo.jungletree.network.SecurityUtils;
import im.octo.jungletree.network.message.login.EncryptionKeyRequestMessage;
import im.octo.jungletree.network.message.login.LoginStartMessage;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class LoginStartHandler implements MessageHandler<JSession, LoginStartMessage> {

    private static final String UUID_PREFIX = "OfflinePlayer:";

    @Override
    public void handle(JSession session, LoginStartMessage message) {
        Server server = Rainforest.getServer();
        String username = message.getUsername();

        if (server.isOnlineMode()) {
            String sessionId = session.getSessionId();
            byte[] publicKey = SecurityUtils.generateX509Key(server.getKeyPair().getPublic()).getEncoded();
            byte[] verifyToken = SecurityUtils.generateVerifyToken();

            session.setUsername(username);
            session.setVerifyToken(verifyToken);
            session.send(new EncryptionKeyRequestMessage(sessionId, publicKey, verifyToken));
        } else {
            ProxyData proxy = session.getProxyData();

            PlayerProfile profile;
            if (proxy == null) {
                UUID uuid = UUID.nameUUIDFromBytes((UUID_PREFIX + username).getBytes(StandardCharsets.UTF_8));
                profile = new PlayerProfile(uuid, username);
            } else {
                profile = proxy.getProfile();
                if (profile == null) {
                    profile = proxy.getProfile(username);
                }
            }

            Injector guice = server.getGuice();
            EventService eventService = guice.getInstance(EventService.class);

            AsyncPlayerPreLoginEvent event = new AsyncPlayerPreLoginEvent(
                    profile.getUuid(),
                    username,
                    session.getAddress()
            );
            eventService.call(event);

            if (event.getLoginResult() != LoginResult.ALLOWED) {
                session.disconnect(event.getKickMessage(), true);
                return;
            }

            PlayerProfile result = profile;
            TaskScheduler scheduler = guice.getInstance(TaskScheduler.class);
            scheduler.execute(() -> session.setPlayer(result));
        }
    }
}
