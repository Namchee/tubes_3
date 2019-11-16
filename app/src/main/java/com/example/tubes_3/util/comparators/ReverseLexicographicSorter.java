package com.example.tubes_3.util.comparators;

import com.example.tubes_3.model.MangaRaw;

import java.util.Comparator;

public class ReverseLexicographicSorter implements Comparator<MangaRaw> {
    @Override
    public int compare(MangaRaw mangaRaw, MangaRaw t1) {
        return t1.getTitle().compareTo(mangaRaw.getTitle());
    }
}
