package com.example.tubes_3.presenters;

import androidx.recyclerview.widget.SortedList;

import com.example.tubes_3.model.MangaRaw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MangaPresenter {
    private List<MangaRaw> mangaRawList;

    public MangaPresenter() {
        this.mangaRawList = new ArrayList<>();
    }

    public MangaPresenter(List<MangaRaw> mangaRawList) {
        this.mangaRawList = mangaRawList;
    }

    public void addManga(MangaRaw mangaRaw) {
        this.mangaRawList.add(mangaRaw);
    }

    public List<MangaRaw> getMangaRawList() {
        return this.mangaRawList;
    }

    public MangaRaw getManga(int idx) {
        return this.mangaRawList.get(idx);
    }

    public int getSize() {
        return this.mangaRawList.size();
    }

    public void clearPresenter() {
        this.mangaRawList.clear();
    }

    public void sort(Comparator<MangaRaw> comparator) {
        Collections.sort(this.mangaRawList, comparator);
    }
}
