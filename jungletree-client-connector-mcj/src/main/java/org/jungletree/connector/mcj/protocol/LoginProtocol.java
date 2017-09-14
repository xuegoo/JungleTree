package org.jungletree.connector.mcj.protocol;

import org.jungletree.connector.mcj.codec.KickCodec;
import org.jungletree.connector.mcj.codec.SetCompressionCodec;
import org.jungletree.connector.mcj.codec.login.EncryptionKeyRequestCodec;
import org.jungletree.connector.mcj.codec.login.EncryptionKeyResponseCodec;
import org.jungletree.connector.mcj.codec.login.LoginStartCodec;
import org.jungletree.connector.mcj.codec.login.LoginSuccessCodec;
import org.jungletree.connector.mcj.handler.login.EncryptionKeyResponseHandler;
import org.jungletree.connector.mcj.handler.login.LoginStartHandler;
import org.jungletree.connector.mcj.message.KickMessage;
import org.jungletree.connector.mcj.message.SetCompressionMessage;
import org.jungletree.connector.mcj.message.login.EncryptionKeyRequestMessage;
import org.jungletree.connector.mcj.message.login.EncryptionKeyResponseMessage;
import org.jungletree.connector.mcj.message.login.LoginStartMessage;
import org.jungletree.connector.mcj.message.login.LoginSuccessMessage;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.scheduler.SchedulerService;

import javax.inject.Inject;

public class LoginProtocol extends JProtocol {

    @Inject
    public LoginProtocol(ClientConnectorResourceService resource, SchedulerService scheduler) {
        super("LOGIN", 5, resource, scheduler);

        inbound(0x00, LoginStartMessage.class, LoginStartCodec.class, LoginStartHandler.class);
        inbound(0x01, EncryptionKeyResponseMessage.class, EncryptionKeyResponseCodec.class, EncryptionKeyResponseHandler.class);

        outbound(0x00, KickMessage.class, KickCodec.class);
        outbound(0x01, EncryptionKeyRequestMessage.class, EncryptionKeyRequestCodec.class);
        outbound(0x02, LoginSuccessMessage.class, LoginSuccessCodec.class);
        outbound(0x03, SetCompressionMessage.class, SetCompressionCodec.class);
    }
}
