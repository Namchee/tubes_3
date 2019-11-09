package com.example.tubes_3.model;

public enum URL_BASE {
    API("https://www.mangaeden.com/api/list/0/"),
    IMAGE_SRC("https://cdn.mangaeden.com/mangasimg/");

    private String url;

    URL_BASE(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
