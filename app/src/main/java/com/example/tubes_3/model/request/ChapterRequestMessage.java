package com.example.tubes_3.model.request;

import com.example.tubes_3.model.Chapter;
import com.example.tubes_3.model.RequestMessage;

public class ChapterRequestMessage extends RequestMessage {
    private final Chapter chapter;

    public ChapterRequestMessage(Chapter chapter) {
        super(2);
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return chapter;
    }
}
