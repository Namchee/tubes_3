package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.Chapter;

public class ChapterResponseMessage extends ResponseMessage {
    private Chapter chapterDetail;

    public ChapterResponseMessage(Chapter chapterDetail) {
        super(RESPONSE_DETAIL);
        this.chapterDetail = chapterDetail;
    }

    public Chapter getChapterDetail() {
        return this.chapterDetail;
    }
}
