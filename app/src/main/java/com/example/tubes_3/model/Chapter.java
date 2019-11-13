package com.example.tubes_3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chapter {
    private String chapterNum;
    private String id, title;

    private Date lastUpdated;

    private List<String> imgUrl;

    public Chapter(String chapterNum, String id, String title, Date lastUpdated) {
        this.chapterNum = chapterNum;
        this.id = id;
        this.title = title;
        this.lastUpdated = lastUpdated;

        this.imgUrl = new ArrayList<>();
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

    public void addImgUrl(String url) {
        this.imgUrl.add(url);
    }
}
