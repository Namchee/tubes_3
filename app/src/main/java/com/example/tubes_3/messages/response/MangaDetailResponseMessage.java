package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.Manga;

public class MangaDetailResponseMessage extends ResponseMessage {
    public Manga mangaDetail;

    public MangaDetailResponseMessage(Manga mangaDetail) {
        super(RESPONSE_DETAIL);
        this.mangaDetail = mangaDetail;
    }

    public Manga getMangaDetail() {
        return this.mangaDetail;
    }
}
