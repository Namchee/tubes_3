package com.example.tubes_3.messages;

public class ResponseMessage {
    public static final int RESPONSE_ALL = 0;
    public static final int RESPONSE_DETAIL = 1;
    public static final int RESPONSE_CHAPTER = 2;
    public static final int RESPONSE_FAVORITE = 3;
    public static final int RESPONSE_HISTORY = 4;

    private int messageType;

    public ResponseMessage() {
        this.messageType = RESPONSE_ALL;
    }

    public ResponseMessage(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageType() {
        return this.messageType;
    }
}
