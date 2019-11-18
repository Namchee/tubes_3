package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.Chapter;

import java.util.List;

public class ChapterResponseMessage extends ResponseMessage {
    private List<String> imageList;

    public ChapterResponseMessage(List<String> imageList) {
        super(RESPONSE_CHAPTER);
        this.imageList = imageList;
    }

    public List<String> getImageList() {
        return imageList;
    }
}
