package com.example.tubes_3.util.comparators;

import com.example.tubes_3.model.MangaRaw;

import java.util.Comparator;

public class LastUpdatedSorter implements Comparator<MangaRaw> {
    @Override
    public int compare(MangaRaw mangaRaw, MangaRaw t1) {
        if (mangaRaw.getLastUpdated() == null) {
            return 1;
        }

        if (t1.getLastUpdated() == null) {
            return -1;
        }

        if (mangaRaw.getLastUpdated() == null && t1.getLastUpdated() == null) {
            return -1;
        }

        return t1.getLastUpdated().compareTo(mangaRaw.getLastUpdated());
    }
}
