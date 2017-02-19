package network.message.entity;

import com.flowpowered.network.Message;

public class CollectItemMessage implements Message {

    private final int id;
    private final int collector;
    private final int count;

    public CollectItemMessage(int id, int collector, int count) {
        this.id = id;
        this.collector = collector;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public int getCollector() {
        return collector;
    }

    public int getCount() {
        return count;
    }
}
