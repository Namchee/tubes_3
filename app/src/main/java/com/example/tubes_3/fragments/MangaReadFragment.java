package com.example.tubes_3.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.model.MangaDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class MangaReadFragment extends Fragment {
    public MangaReadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_read, container, false);

        return view;
    }

}
