package org.jungletree.messenger;

import org.jungletree.rainforest.messaging.Message;

public class DummyMessage implements Message {

    private String sender;
    private String recipient;
    private String value;

    public DummyMessage() {
    }

    @Override
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
