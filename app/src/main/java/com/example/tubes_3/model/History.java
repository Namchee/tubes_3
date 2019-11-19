package com.example.tubes_3.model;

public class History {
    private String idManga;
    private int lastChapter;

    public History(String idManga, int lastChapter) {
        this.idManga = idManga;
        this.lastChapter = lastChapter;
    }

    public String getIdManga() {
        return idManga;
    }

    public void setIdManga(String idManga) {
        this.idManga = idManga;
    }

    public int getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(int lastChapter) {
        this.lastChapter = lastChapter;
    }
}
