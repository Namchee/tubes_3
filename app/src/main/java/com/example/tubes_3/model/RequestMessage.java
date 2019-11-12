package com.example.tubes_3.model;

public class RequestMessage {
    private final int messageType;

    public RequestMessage() {
        this.messageType = 0;
    }

    public RequestMessage(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageType() {
        return this.messageType;
    }
}
