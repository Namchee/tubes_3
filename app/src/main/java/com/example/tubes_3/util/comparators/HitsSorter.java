package com.example.tubes_3.util.comparators;

import com.example.tubes_3.model.MangaRaw;

import java.util.Comparator;

public class HitsSorter implements Comparator<MangaRaw> {
    @Override
    public int compare(MangaRaw mangaRaw, MangaRaw t1) {
        if (mangaRaw.getHits() > t1.getHits()) {
            return -1;
        } else if (mangaRaw.getHits() == t1.getHits()) {
            return 0;
        } else {
            return 1;
        }
    }
}
