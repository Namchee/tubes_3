package com.example.tubes_3.model;

import java.security.Timestamp;
import java.util.Date;

public class Chapter {
    private String chapterNum;
    private String id, title;

    private Date lastUpdated;

    public Chapter(String chapterNum, String id, String title, Timestamp lastUpdated) {
        this.chapterNum = chapterNum;
        this.id = id;
        this.title = title;
        this.lastUpdated = lastUpdated.getTimestamp();
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getId() {
        return id;
    }

    public String getChapterNum() {
        return chapterNum;
    }

    public String getTitle() {
        return title;
    }
}
