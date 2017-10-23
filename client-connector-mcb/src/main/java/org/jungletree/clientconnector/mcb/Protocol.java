package org.jungletree.clientconnector.mcb;

import org.jungletree.clientconnector.mcb.codec.crypto.ServerToClientHandshakeCodec;
import org.jungletree.clientconnector.mcb.codec.handshake.LoginCodec;
import org.jungletree.clientconnector.mcb.codec.handshake.PlayStateCodec;
import org.jungletree.clientconnector.mcb.codec.resourcepack.ResourcePackInfoCodec;
import org.jungletree.clientconnector.mcb.codec.resourcepack.ResourcePackStackCodec;
import org.jungletree.clientconnector.mcb.handler.handshake.LoginHandler;
import org.jungletree.clientconnector.mcb.message.crypto.ServerToClientHandshakeMessage;
import org.jungletree.clientconnector.mcb.message.handshake.LoginMessage;
import org.jungletree.clientconnector.mcb.message.handshake.PlayStateMessage;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackInfoMessage;
import org.jungletree.clientconnector.mcb.message.resourcepack.ResourcePackStackMessage;
import org.jungletree.clientconnector.mcb.messaging.JwtValidationHandler;
import org.jungletree.rainforest.auth.messages.JwtAuthReponseMessage;
import org.jungletree.rainforest.messaging.MessagingService;

public class Protocol {

    private final MessagingService messaging;
    private final CodecRegistration registration;

    public Protocol(MessagingService messaging) {
        this.messaging = messaging;
        this.registration = registerCodecs();
    }

    public CodecRegistration getCodecRegistration() {
        return registration;
    }

    private CodecRegistration registerCodecs() {
        CodecRegistration reg = new CodecRegistration();

        LoginHandler loginHandler = new LoginHandler(messaging);

        reg.codec(0x01, LoginMessage.class, LoginCodec.class);
        reg.handler(LoginMessage.class, loginHandler);

        reg.codec(0x02, PlayStateMessage.class, PlayStateCodec.class);
        reg.codec(0x03, ServerToClientHandshakeMessage.class, ServerToClientHandshakeCodec.class);

        reg.codec(0x06, ResourcePackInfoMessage.class, ResourcePackInfoCodec.class);
        reg.codec(0x07, ResourcePackStackMessage.class, ResourcePackStackCodec.class);

        return reg;
    }
}
