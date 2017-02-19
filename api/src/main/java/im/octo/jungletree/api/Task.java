package im.octo.jungletree.api;

import java.util.UUID;

public interface Task<R> {

    UUID TASK_UUID = UUID.randomUUID();

    default boolean isParallel() {
        return true;
    }

    boolean hasExecuted();

    boolean isCancelled();

    void execute();

    R getResult();
}
