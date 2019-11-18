package com.example.tubes_3.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.util.ServiceWorker;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MangaReadFragment extends Fragment {
    @BindView(R.id.detail_progress_loader)
    ProgressBar loader;


    public MangaReadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_read, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);

        this.loader.setVisibility(View.VISIBLE);

        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        ServiceWorker.getInstance(this.getContext()).getMangaDetail(mangaRaw.getId());
    }

}
