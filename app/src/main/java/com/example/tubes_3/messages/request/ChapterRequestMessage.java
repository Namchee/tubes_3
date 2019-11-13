package com.example.tubes_3.messages.request;

import com.example.tubes_3.model.Chapter;
import com.example.tubes_3.messages.RequestMessage;

public class ChapterRequestMessage extends RequestMessage {
    private final Chapter chapter;

    public ChapterRequestMessage(Chapter chapter) {
        super(REQUEST_CHAPTER);
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return chapter;
    }
}
