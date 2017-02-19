package im.octo.jungletree.network.protocol;

import im.octo.jungletree.network.codec.KickCodec;
import im.octo.jungletree.network.codec.SetCompressionCodec;
import im.octo.jungletree.network.codec.login.EncryptionKeyRequestCodec;
import im.octo.jungletree.network.codec.login.EncryptionKeyResponseCodec;
import im.octo.jungletree.network.codec.login.LoginStartCodec;
import im.octo.jungletree.network.codec.login.LoginSuccessCodec;
import im.octo.jungletree.network.handler.login.EncryptionKeyResponseHandler;
import im.octo.jungletree.network.handler.login.LoginStartHandler;
import im.octo.jungletree.network.message.KickMessage;
import im.octo.jungletree.network.message.SetCompressionMessage;
import im.octo.jungletree.network.message.login.EncryptionKeyRequestMessage;
import im.octo.jungletree.network.message.login.EncryptionKeyResponseMessage;
import im.octo.jungletree.network.message.login.LoginStartMessage;
import im.octo.jungletree.network.message.login.LoginSuccessMessage;

public class LoginProtocol extends JProtocol {

    public LoginProtocol() {
        super("LOGIN", 5);

        inbound(0x00, LoginStartMessage.class, LoginStartCodec.class, LoginStartHandler.class);
        inbound(0x01, EncryptionKeyResponseMessage.class, EncryptionKeyResponseCodec.class, EncryptionKeyResponseHandler.class);

        outbound(0x00, KickMessage.class, KickCodec.class);
        outbound(0x01, EncryptionKeyRequestMessage.class, EncryptionKeyRequestCodec.class);
        outbound(0x02, LoginSuccessMessage.class, LoginSuccessCodec.class);
        outbound(0x03, SetCompressionMessage.class, SetCompressionCodec.class);
    }
}
