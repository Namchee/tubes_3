package com.example.tubes_3.util;

public enum URL_BASE {
    MANGA_ALL("https://www.mangaeden.com/api/list/0/"),
    MANGA_DETAIL("https://www.mangaeden.com/api/manga/"),
    CHAPTERS("https://www.mangaeden.com/api/chapter/"),
    IMAGE_SRC("https://cdn.mangaeden.com/mangasimg/");

    private String url;

    URL_BASE(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
