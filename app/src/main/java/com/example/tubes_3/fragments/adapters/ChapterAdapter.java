package com.example.tubes_3.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_3.R;
import com.example.tubes_3.presenters.ChapterPresenter;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterViewHolder> {
    private LayoutInflater inflater;
    private ChapterPresenter presenter;

    public ChapterAdapter(Context ctx, ChapterPresenter presenter) {
        this.inflater = LayoutInflater.from(ctx);
        this.presenter = presenter;

        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.chapter_layout, parent, false);

        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.setChapter(this.presenter.getChapter(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.presenter.getSize();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
