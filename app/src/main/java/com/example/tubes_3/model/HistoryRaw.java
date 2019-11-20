package com.example.tubes_3.model;

public class HistoryRaw {
    private String mangaId;
    private double lastChapter;

    public HistoryRaw(String mangaId, double lastChapter) {
        this.mangaId = mangaId;
        this.lastChapter = lastChapter;
    }

    public String getMangaId() {
        return mangaId;
    }

    public double getLastChapter() {
        return lastChapter;
    }
}
