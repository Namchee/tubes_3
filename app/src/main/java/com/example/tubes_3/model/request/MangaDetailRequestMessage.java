package com.example.tubes_3.model.request;

import com.example.tubes_3.model.Manga;
import com.example.tubes_3.model.RequestMessage;

public class MangaDetailRequestMessage extends RequestMessage {
    private final Manga manga;

    public MangaDetailRequestMessage(Manga manga) {
        super(1);
        this.manga = manga;
    }

    public Manga getManga() {
        return manga;
    }
}
