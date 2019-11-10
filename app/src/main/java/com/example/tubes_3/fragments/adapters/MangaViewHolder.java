package com.example.tubes_3.fragments.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.model.Manga;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MangaViewHolder {
    @BindView(R.id.manga_pic) ImageView mangaPic;
    @BindView(R.id.manga_title) TextView mangaTitle;
    @BindView(R.id.manga_status) TextView mangaStatus;

    public MangaViewHolder(View view) {
        ButterKnife.bind(this, view);
    }

    public void setManga(Manga manga) {
        if (manga.getImgUrl() != null && manga.getImgUrl() != "") {
            Picasso.get().load(Uri.decode(manga.getImgUrl())).placeholder(R.drawable.ic_progress_animation).into(this.mangaPic);
        } else {
            this.mangaPic.setImageResource(R.drawable.ic_no);
        }

        this.mangaTitle.setText(manga.getTitle());
        this.mangaStatus.setText(manga.getStatus());
    }
}
