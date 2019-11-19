package com.example.tubes_3.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_3.R;
import com.example.tubes_3.messages.request.ChapterRequestMessage;
import com.example.tubes_3.model.Chapter;
import com.example.tubes_3.model.HistoryRaw;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.presenters.ChapterPresenter;
import com.example.tubes_3.sharedPreference.MangaStorage;

import org.greenrobot.eventbus.EventBus;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterViewHolder> {
    private LayoutInflater inflater;
    private ChapterPresenter presenter;
    private MangaRaw mangaRaw;

    public ChapterAdapter(Context ctx, ChapterPresenter presenter, MangaRaw raw) {
        this.inflater = LayoutInflater.from(ctx);
        this.presenter = presenter;
        this.mangaRaw = raw;

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
        Chapter relevantChapter = this.presenter.getChapter(position);

        holder.setChapter(relevantChapter);

        holder.itemView.setOnClickListener((View view) -> {
            MangaStorage storage = new MangaStorage(view.getContext());
            storage.addHistory(new HistoryRaw(this.mangaRaw.getId(), relevantChapter.getChapterNum()));

            EventBus.getDefault().postSticky(new ChapterRequestMessage(relevantChapter));
        });
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
