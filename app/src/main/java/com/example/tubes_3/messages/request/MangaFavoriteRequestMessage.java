package com.example.tubes_3.messages.request;

import com.example.tubes_3.messages.RequestMessage;

import java.util.List;

public class MangaFavoriteRequestMessage extends RequestMessage {
    private List<String> mangaIds;

    public MangaFavoriteRequestMessage(List<String> mangaIds) {
        super(REQUEST_FAVORITE);
        this.mangaIds = mangaIds;
    }

    public List<String> getMangaIds() {
        return mangaIds;
    }
}
