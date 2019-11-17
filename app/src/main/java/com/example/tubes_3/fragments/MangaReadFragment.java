package com.example.tubes_3.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.model.MangaDetail;
import com.example.tubes_3.model.MangaRaw;

import java.util.ArrayList;

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


    public static MangaReadFragment createInstance(){//parameternya dari objek chappter.url
        Bundle args = new Bundle();
        args.putStringArrayList("URL",new ArrayList<String>()); //value: url dari chapter
        MangaReadFragment mrf = new MangaReadFragment();
        mrf.setArguments(args);
        return mrf;
    }

}
