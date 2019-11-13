package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;

public class MangaDetailResponseMessage extends ResponseMessage {
    public MangaDetail mangaRawDetail;

    public MangaDetailResponseMessage(MangaDetail mangaRawDetail) {
        super(RESPONSE_DETAIL);
        this.mangaRawDetail = mangaRawDetail;
    }

    public MangaDetail getMangaRawDetail() {
        return this.mangaRawDetail;
    }
}
