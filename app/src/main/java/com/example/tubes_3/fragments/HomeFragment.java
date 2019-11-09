package com.example.tubes_3.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubes_3.R;
import com.example.tubes_3.fragments.home.DisplayFragment;
import com.example.tubes_3.model.URL_BASE;
import com.example.tubes_3.presenters.MangaPresenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FragmentManager homeFm;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.homeFm = this.getChildFragmentManager();
        FragmentTransaction ft = this.homeFm.beginTransaction();

        DisplayFragment displayFragment = new DisplayFragment();

        ft.add(R.id.home_fragment_container, displayFragment,"");

        ft.commit();

        return view;
    }


}
