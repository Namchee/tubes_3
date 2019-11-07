package com.example.tubes_3.presenters;

import com.example.tubes_3.model.Manga;

import java.util.ArrayList;
import java.util.List;

public class MangaPresenter {
    private List<Manga> mangaList;

    public MangaPresenter() {
        this.mangaList = new ArrayList<>();
    }

    public void addManga(Manga manga) {
        this.mangaList.add(manga);
    }

    public List<Manga> getMangaList() {
        return this.mangaList;
    }

    public Manga getManga(int idx) {
        return this.mangaList.get(idx);
    }
}
