package com.example.tubes_3.model;

import java.util.Date;
import java.util.List;

public class MangaDetail {
    private String imgUrl, title, author, artist, desc, status, url;
    private int hits;
    private Date createdAt, lastUpdated;

    private List<String> categories;
    private List<Chapter> chapters;

    public MangaDetail(String imgUrl, String title, String author, String artist, String desc, String status, List<String> categories, Date createdAt, Date lastUpdated, List<Chapter> chapterList, int hits, String url) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.status = status;
        this.artist = artist;
        this.categories = categories;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
        this.chapters = chapterList;
        this.hits = hits;
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getArtist() {
        return artist;
    }

    public String getDesc() {
        return desc;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getStatus() {
        return status;
    }

    public int getHits() {
        return hits;
    }

    public String getUrl() {
        return url;
    }
}
