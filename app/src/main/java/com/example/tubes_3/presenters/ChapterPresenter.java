package com.example.tubes_3.presenters;

import com.example.tubes_3.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterPresenter {
    private List<Chapter> chapterList;

    public ChapterPresenter() {
        this.chapterList = new ArrayList<>();
    }

    public ChapterPresenter(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

    public void addChapter(Chapter chapter) {
        this.chapterList.add(chapter);
    }

    public List<Chapter> getChapterList() {
        return this.chapterList;
    }

    public Chapter getChapter(int idx) {
        return this.chapterList.get(idx);
    }

    public int getSize() {
        return this.chapterList.size();
    }
}
