package com.example.tubes_3.model;

public class HistoryDetail {
    private MangaRaw mangaRaw;
    private Chapter chapter;

    public HistoryDetail(MangaRaw mangaRaw, Chapter chapter) {
        this.mangaRaw = mangaRaw;
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public MangaRaw getMangaRaw() {
        return mangaRaw;
    }
}
