package com.example.tubes_3.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.example.tubes_3.R;
import com.example.tubes_3.model.Manga;
import com.example.tubes_3.presenters.MangaPresenter;

public class MangaAdapter extends BaseAdapter {
    private Context ctx;
    private MangaPresenter presenter;

    public MangaAdapter(Context ctx, MangaPresenter presenter) {
        this.ctx = ctx;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return this.presenter.getSize();
    }

    @Override
    public Object getItem(int i) {
        return this.presenter.getManga(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MangaViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(this.ctx).inflate(R.layout.manga_layout, viewGroup, false);
            viewHolder = new MangaViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MangaViewHolder)view.getTag();
        }

        Manga manga = (Manga)this.getItem(i);

        viewHolder.setManga(manga);

        return view;
    }
}
