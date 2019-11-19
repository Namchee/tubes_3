package com.example.tubes_3.messages.response;

import com.example.tubes_3.messages.ResponseMessage;
import com.example.tubes_3.model.MangaRaw;

import java.util.List;

public class MangaFavoriteResponseMessage extends ResponseMessage {
    private List<MangaRaw> favoritesList;

    public MangaFavoriteResponseMessage(List<MangaRaw> favoritesList) {
        super(RESPONSE_FAVORITE);
        this.favoritesList = favoritesList;
    }

    public List<MangaRaw> getFavoritesList() {
        return favoritesList;
    }
}
