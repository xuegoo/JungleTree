package org.jungletree.connector.mcj.protocol;

import com.flowpowered.network.Codec;
import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.flowpowered.network.exception.IllegalOpcodeException;
import com.flowpowered.network.exception.UnknownPacketException;
import com.flowpowered.network.protocol.AbstractProtocol;
import com.flowpowered.network.service.CodecLookupService;
import com.flowpowered.network.service.HandlerLookupService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.jungletree.rainforest.connector.ClientConnectorResourceService;
import org.jungletree.rainforest.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static com.flowpowered.network.util.ByteBufUtils.readVarInt;
import static com.flowpowered.network.util.ByteBufUtils.writeVarInt;

public class JProtocol extends AbstractProtocol {

    private static final Logger log = LoggerFactory.getLogger(JProtocol.class);

    private final CodecLookupService inbound;
    private final CodecLookupService outbound;
    private final ClientConnectorResourceService resource;
    private final SchedulerService scheduler;
    private final HandlerLookupService handlers;

    public JProtocol(String name, int highestOpcode, ClientConnectorResourceService resource, SchedulerService scheduler) {
        super(name);

        this.resource = resource;
        this.scheduler = scheduler;

        this.inbound = new CodecLookupService(highestOpcode + 1);
        this.outbound = new CodecLookupService(highestOpcode + 1);
        this.handlers = new HandlerLookupService(resource, scheduler);
    }

    protected <M extends Message, C extends Codec<? super M>, H extends MessageHandler<?, ? super M>> void inbound(int opcode, Class<M> message, Class<C> codec, Class<H> handler) {
        try {
            inbound.bind(message, codec, opcode);
            handlers.bind(message, handler);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            getLogger().error("Error registering inbound " + opcode + " in " + getName(), e);
        }
    }

    protected <M extends Message, C extends Codec<? super M>> void outbound(int opcode, Class<M> message, Class<C> codec) {
        try {
            outbound.bind(message, codec, opcode);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            getLogger().error("Error registering outbound " + opcode + " in " + getName(), e);
        }
    }

    @Override
    public <M extends Message> MessageHandler<?, M> getMessageHandle(Class<M> clazz) {
        MessageHandler<?, M> handler = handlers.find(clazz);
        if (handler == null) {
            log.warn("No message handler for: {} in {}", clazz.getSimpleName(), getName());
        }
        return handler;
    }

    @Override
    public <M extends Message> Codec.CodecRegistration getCodecRegistration(Class<M> clazz) {
        Codec.CodecRegistration reg = outbound.find(clazz);
        if (reg == null) {
            log.warn("No codec to write: {} in {}", clazz.getSimpleName(), getName());
        }
        return reg;
    }

    @Override
    public Codec<?> readHeader(ByteBuf buf) throws UnknownPacketException {
        int opcode = -1;
        try {
            opcode = readVarInt(buf);
            return inbound.find(opcode);
        } catch (IOException ex) {
            throw new UnknownPacketException("Failed to read packet data (corrupt?)", opcode, 0);
        } catch (IllegalOpcodeException ex) {
            throw new UnknownPacketException("Opcode received is not a registered codec on the server!", opcode, 0);
        }
    }

    @Override
    @Deprecated
    public ByteBuf writeHeader(ByteBuf out, Codec.CodecRegistration codec, ByteBuf data) {
        ByteBuf opcodeBuffer = Unpooled.buffer(5);
        writeVarInt(opcodeBuffer, codec.getOpcode());
        writeVarInt(out, opcodeBuffer.readableBytes() + data.readableBytes());
        writeVarInt(out, codec.getOpcode());
        opcodeBuffer.release();
        return out;
    }
}
