package com.example.tubes_3.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tubes_3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MangaDetailFragment extends Fragment {


    public MangaDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_detail, container, false);

        return view;
    }

}