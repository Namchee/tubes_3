package com.example.tubes_3.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.model.MangaDetail;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MangaDetailFragment extends Fragment {
    MangaDetail mangaDetail;

    @BindView(R.id.detail_manga_picture) ImageView mangaPic;
    @BindView(R.id.detail_manga_author) TextView tvAuthor;
    @BindView(R.id.detail_manga_artist) TextView tvArtist;
    @BindView(R.id.detail_manga_status) TextView tvStatus;
    @BindView(R.id.detail_manga_synopsis) TextView tvSynopsis;
    @BindView(R.id.detail_manga_chapters) ListView lvChapters;

    public MangaDetailFragment() {
        // Required empty public constructor
    }

    public MangaDetailFragment(MangaDetail mangaDetail) {
        this.mangaDetail = mangaDetail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_detail, container, false);

        return view;
    }

}
