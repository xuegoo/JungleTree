package im.octo.jungletree.network.message.play.player;

import com.flowpowered.network.Message;

public class TransactionMessage implements Message {

    private final int id;
    private final int transaction;
    private final boolean accepted;

    public TransactionMessage(int id, int transaction, boolean accepted) {
        this.id = id;
        this.transaction = transaction;
        this.accepted = accepted;
    }

    public int getId() {
        return id;
    }

    public int getTransaction() {
        return transaction;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
