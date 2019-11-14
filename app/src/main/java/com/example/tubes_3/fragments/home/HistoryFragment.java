package com.example.tubes_3.fragments.home;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.presenters.MangaPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        return view;
    }

}
