package com.example.tubes_3.messages;

public class RequestMessage {
    public static final int REQUEST_ALL = 0;
    public static final int REQUEST_DETAIL = 1;
    public static final int REQUEST_CHAPTER = 2;
    public static final int REQUEST_FAVORITE = 3;
    public static final int REQUEST_HISTORY = 4;

    private final int messageType;

    public RequestMessage() {
        this.messageType = REQUEST_ALL;
    }

    public RequestMessage(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageType() {
        return this.messageType;
    }
}
