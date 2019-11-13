package com.example.tubes_3.messages.request;

import com.example.tubes_3.model.Manga;
import com.example.tubes_3.messages.RequestMessage;

public class MangaDetailRequestMessage extends RequestMessage {
    private final Manga manga;

    public MangaDetailRequestMessage(Manga manga) {
        super(REQUEST_DETAIL);
        this.manga = manga;
    }

    public Manga getManga() {
        return manga;
    }
}
