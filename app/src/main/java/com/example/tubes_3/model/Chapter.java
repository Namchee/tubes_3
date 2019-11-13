package com.example.tubes_3.model;

import java.util.Date;

public class Chapter {
    private int chapterNum;
    private String id, title;

    private Date lastUpdated;

    public Chapter(int chapterNum, String id, String title, Date lastUpdated) {
        this.chapterNum = chapterNum;
        this.id = id;
        this.title = title;
        this.lastUpdated = lastUpdated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getId() {
        return id;
    }

    public int getChapterNum() {
        return chapterNum;
    }

    public String getTitle() {
        return title;
    }
}
