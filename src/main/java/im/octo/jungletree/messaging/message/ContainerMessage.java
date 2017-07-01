package im.octo.jungletree.messaging.message;

import im.octo.jungletree.rainforest.messaging.Message;
import im.octo.jungletree.rainforest.storage.Container;

public class ContainerMessage implements Message {

    private String name;

    private Request request;

    private Container container;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public enum Request {
        OPEN,
        CLOSE
    }
}