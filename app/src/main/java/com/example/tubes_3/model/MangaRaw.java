package com.example.tubes_3.model;

import java.util.Date;
import java.util.List;

public class MangaRaw {
    private int hits;
    private String id, title, imgUrl, status;
    private List<String> categories;

    private Date lastUpdated;

    public MangaRaw(String id, String title, String imgUrl, Date lastUpdated, List<String> categories, String status, int hits) {
        this.id = id;
        this.title = title;
        this.lastUpdated = lastUpdated;
        this.imgUrl = imgUrl;

        this.categories = categories;
        this.status = status;
        this.hits = hits;
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

    public List<String> getCategories() {
        return categories;
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
