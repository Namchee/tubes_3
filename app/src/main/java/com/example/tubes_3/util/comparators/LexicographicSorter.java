package com.example.tubes_3.util.comparators;

import com.example.tubes_3.model.MangaRaw;

import java.util.Comparator;

public class LexicographicSorter implements Comparator<MangaRaw> {
    @Override
    public int compare(MangaRaw mangaRaw, MangaRaw t1) {
        return mangaRaw.getTitle().compareTo(t1.getTitle());
    }
}
