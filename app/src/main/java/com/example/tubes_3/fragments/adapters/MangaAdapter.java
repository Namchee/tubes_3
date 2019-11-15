package com.example.tubes_3.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_3.R;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.presenters.MangaPresenter;

import org.greenrobot.eventbus.EventBus;

public class MangaAdapter extends RecyclerView.Adapter<MangaViewHolder> {
    private MangaPresenter presenter;
    private LayoutInflater inflater;

    public MangaAdapter(Context ctx, MangaPresenter presenter) {
        this.inflater = LayoutInflater.from(ctx);
        this.presenter = presenter;

        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.manga_layout, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        holder.setManga(this.presenter.getManga(position));
    }

    @Override
    public int getItemCount() {
        return this.presenter.getSize();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
