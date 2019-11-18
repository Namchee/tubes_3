package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.MangaRaw;

import java.util.List;

public class MangaListResponseMessage extends ResponseMessage {
    List<MangaRaw> mangaRawList;

    public MangaListResponseMessage(List<MangaRaw> mangaRawList) {
        super(RESPONSE_ALL);
        this.mangaRawList = mangaRawList;
    }

    public List<MangaRaw> getMangaRawList() {
        return this.mangaRawList;
    }
}
