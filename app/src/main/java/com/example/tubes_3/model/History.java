package com.example.tubes_3.model;

public class History {
    private String idManga;
    private int lastChapter;
    private int lastPage;

    public History(String idManga, int lastChapter, int lastPage) {
        this.idManga = idManga;
        this.lastChapter = lastChapter;
        this.lastPage = lastPage;
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

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
}
