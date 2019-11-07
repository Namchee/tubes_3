package com.example.tubes_3.model;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manga {
    private int chaptersLength;
    private String id, title, desc, imgUrl;
    private List<String> categories;

    private Date created, lastUpdated;

    private List<Chapter> chapters;

    public Manga(String id, String title, String imgUrl, Timestamp lastUpdated, List<String> categories, int chaptersLength) {
        this.id = id;
        this.title = title;
        this.lastUpdated = lastUpdated.getTimestamp();
        this.imgUrl = imgUrl;

        this.categories = categories;
        this.chaptersLength = chaptersLength;

        this.chapters = new ArrayList<>();
    }

    public void addChapter(Chapter chapter) {
        this.chapters.add(chapter);
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCreated(Timestamp created) {
        this.created = created.getTimestamp();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Date getCreated() {
        return created;
    }

    public int getChaptersLength() {
        return chaptersLength;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getDesc() {
        return desc;
    }
}
