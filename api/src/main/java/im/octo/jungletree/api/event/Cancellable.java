package im.octo.jungletree.api.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
