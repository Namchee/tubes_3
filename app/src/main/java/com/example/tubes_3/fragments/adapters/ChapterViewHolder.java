package com.example.tubes_3.fragments.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tubes_3.R;
import com.example.tubes_3.model.Chapter;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.chapter_detail) TextView tvDetail;
    @BindView(R.id.chapter_date) TextView tvDate;

    public ChapterViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);

        if (this.tvDetail == null) {
            this.tvDetail = view.findViewById(R.id.chapter_detail);
        }

        if (this.tvDate == null) {
            this.tvDate = view.findViewById(R.id.chapter_date);
        }
    }

    public void setChapter(Chapter chapter) {
        StringBuilder sb = new StringBuilder();

        sb.append(chapter.getChapterNum());

        if (chapter.getTitle() != null && !chapter.getTitle().isEmpty()) {
            sb.append(" - ");
            sb.append(chapter.getTitle());
        }

        this.tvDetail.setText(sb.toString());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        this.tvDate.setText(dateFormat.format(chapter.getLastUpdated()));
    }
}
