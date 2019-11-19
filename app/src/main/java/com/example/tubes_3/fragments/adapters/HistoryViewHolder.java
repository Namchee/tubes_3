package com.example.tubes_3.fragments.adapters;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_3.R;
import com.example.tubes_3.messages.request.ChapterRequestMessage;
import com.example.tubes_3.model.HistoryDetail;
import com.example.tubes_3.model.HistoryRaw;
import com.example.tubes_3.sharedPreference.MangaStorage;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.history_manga_title) TextView tvTitle;
    @BindView(R.id.history_manga_chapter) TextView tvChapter;
    @BindView(R.id.history_manga_image) ImageView ivImage;

    public HistoryViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setHistory(HistoryDetail history) {
        if (history.getMangaRaw().getImgUrl() != null && history.getMangaRaw().getImgUrl() != "") {
            Picasso.get().load(Uri.decode(history.getMangaRaw().getImgUrl())).placeholder(R.drawable.ic_progress_animation).into(this.ivImage);
        } else {
            this.ivImage.setImageResource(R.drawable.ic_no);
        }

        this.tvTitle.setText(history.getMangaRaw().getTitle());

        StringBuilder str = new StringBuilder(history.getChapter().getChapterNum() + "");
        str.append(" - ");
        str.append(history.getChapter().getTitle());

        this.tvChapter.setText(str.toString());

        this.itemView.setOnClickListener((View view) -> {
            MangaStorage storage = new MangaStorage(view.getContext());
            storage.addHistory(new HistoryRaw(history.getMangaRaw().getId(), history.getChapter().getChapterNum()));

            EventBus.getDefault().postSticky(new ChapterRequestMessage(history.getChapter()));
        });
    }
}
