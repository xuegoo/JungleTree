package im.octo.jungletree.network.message.play.game;

import com.flowpowered.network.Message;

public class StatisticsMessage implements Message {

    private final int count;
    private final Statistic[] statistics;

    public StatisticsMessage(int count, Statistic[] statistics) {
        this.count = count;
        this.statistics = statistics;
    }

    public int getCount() {
        return count;
    }

    public Statistic[] getStatistics() {
        return statistics;
    }

    public static class Statistic {
        private final String name;
        private final int value;

        public Statistic(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }
}
