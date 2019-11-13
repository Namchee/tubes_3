package com.example.tubes_3.messages.request;

import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.messages.RequestMessage;

public class MangaDetailRequestMessage extends RequestMessage {
    private final MangaRaw mangaRaw;

    public MangaDetailRequestMessage(MangaRaw mangaRaw) {
        super(REQUEST_DETAIL);
        this.mangaRaw = mangaRaw;
    }

    public MangaRaw getMangaRaw() {
        return mangaRaw;
    }
}
