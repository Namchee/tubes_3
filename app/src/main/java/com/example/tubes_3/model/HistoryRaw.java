package com.example.tubes_3.model;

public class HistoryRaw {
    private String idManga;
    private int lastChapter;

    public HistoryRaw(String idManga, int lastChapter) {
        this.idManga = idManga;
        this.lastChapter = lastChapter;
    }

    public String getIdManga() {
        return idManga;
    }

    public int getLastChapter() {
        return lastChapter;
    }
}
