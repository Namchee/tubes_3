package com.example.tubes_3.util;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.tubes_3.model.MangaRaw;

import java.util.List;

public class MangaRawDiffUtils extends DiffUtil.Callback {
    private List<MangaRaw> oldList;
    private List<MangaRaw> newList;

    public MangaRawDiffUtils(List<MangaRaw> oldList, List<MangaRaw> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return this.oldList.size();
    }

    @Override
    public int getNewListSize() {
        return this.newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return this.oldList.get(oldItemPosition).getId() == this.newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return this.oldList.get(oldItemPosition).getId() == this.newList.get(newItemPosition).getId();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
