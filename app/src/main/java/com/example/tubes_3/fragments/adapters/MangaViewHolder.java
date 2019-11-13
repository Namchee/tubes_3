package com.example.tubes_3.fragments.adapters;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_3.R;
import com.example.tubes_3.messages.request.MangaDetailRequestMessage;
import com.example.tubes_3.model.MangaRaw;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MangaViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.manga_pic) ImageView mangaPic;
    @BindView(R.id.manga_title) TextView mangaTitle;
    @BindView(R.id.manga_status) TextView mangaStatus;

    public MangaViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setManga(MangaRaw mangaRaw) {
        if (mangaRaw.getImgUrl() != null && mangaRaw.getImgUrl() != "") {
            Picasso.get().load(Uri.decode(mangaRaw.getImgUrl())).placeholder(R.drawable.ic_progress_animation).into(this.mangaPic);
        } else {
            this.mangaPic.setImageResource(R.drawable.ic_no);
        }

        this.mangaTitle.setText(mangaRaw.getTitle());
        this.mangaStatus.setText(mangaRaw.getStatus());

        this.itemView.setOnClickListener((View view) -> {
            EventBus.getDefault().postSticky(new MangaDetailRequestMessage(mangaRaw));
        });
    }
}
