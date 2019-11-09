package com.example.tubes_3.model;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manga {
    private int chaptersLength, hits;
    private String id, title, desc, imgUrl, status;
    private List<String> categories;

    private Date created, lastUpdated;

    private List<Chapter> chapters;

    public Manga(String id, String title, String imgUrl, Date lastUpdated, List<String> categories, String status, int hits) {
        this.id = id;
        this.title = title;
        this.lastUpdated = lastUpdated;
        this.imgUrl = imgUrl;

        this.categories = categories;
        this.status = status;
        this.hits = hits;

        this.chapters = new ArrayList<>();
    }

    public void addChapter(Chapter chapter) {
        this.chapters.add(chapter);
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setCreated(Timestamp created) {
        this.created = created.getTimestamp();
    }

    public void setChaptersLength(int chaptersLength) {
        this.chaptersLength = chaptersLength;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public int getHits() {
        return hits;
    }

    public String getStatus() {
        return status;
    }
}
