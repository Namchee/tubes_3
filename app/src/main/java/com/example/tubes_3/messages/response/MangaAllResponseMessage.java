package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.Manga;

import java.util.List;

public class MangaAllResponseMessage extends ResponseMessage {
    List<Manga> mangaList;

    public MangaAllResponseMessage(List<Manga> mangaList) {
        super(RESPONSE_ALL);
        this.mangaList = mangaList;
    }

    public List<Manga> getMangaList() {
        return this.mangaList;
    }
}
