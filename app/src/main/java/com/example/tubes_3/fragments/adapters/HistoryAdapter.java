package com.example.tubes_3.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_3.R;
import com.example.tubes_3.presenters.HistoryPresenter;
import com.example.tubes_3.sharedPreference.MangaStorage;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private LayoutInflater inflater;
    private HistoryPresenter presenter;

    public HistoryAdapter(Context ctx, HistoryPresenter presenter) {
        this.inflater = LayoutInflater.from(ctx);
        this.presenter = presenter;

        this.setHasStableIds(true);
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.history_layout, parent, false);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.setHistory(this.presenter.getHistoryDetail(position));
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

    public void onSwipe(int position) {
        MangaStorage storage = new MangaStorage(this.inflater.getContext());

        storage.deleteHistory(this.presenter.getHistoryDetail(position).getMangaRaw().getId());

        this.presenter.deleteItem(position);

        this.notifyItemRemoved(position + 1);
    }
}
