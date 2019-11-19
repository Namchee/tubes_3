package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.Chapter;
import com.example.tubes_3.model.MangaRaw;

import java.util.List;

public class MangaHistoryResponseMessage extends ResponseMessage {
    private List<MangaRaw> mangaRawList;
    private List<Chapter> chapterList;

    public MangaHistoryResponseMessage(List<MangaRaw> mangaRawList, List<Chapter> chapters) {
        super(RESPONSE_HISTORY);
        this.mangaRawList = mangaRawList;
        this.chapterList = chapters;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public List<MangaRaw> getMangaRawList() {
        return mangaRawList;
    }
}
