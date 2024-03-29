package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;

public class MangaDetailResponseMessage extends ResponseMessage {
    private MangaDetail mangaDetail;

    public MangaDetailResponseMessage(MangaDetail mangaDetail) {
        super(RESPONSE_DETAIL);
        this.mangaDetail = mangaDetail;
    }

    public MangaDetail getMangaDetail() {
        return this.mangaDetail;
    }
}
