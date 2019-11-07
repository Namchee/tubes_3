package com.example.tubes_3.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
public class HomeFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    @BindView(R.id.manga_view) GridView mangaView;

    int currentPage;
    Unbinder unbinder;

    MangaPresenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.currentPage = 0;
        this.unbinder = ButterKnife.bind(view);

        this.presenter = new MangaPresenter();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String baseApi = URL_BASE.API.getUrl();
        StringBuilder stringBuilder = new StringBuilder(baseApi);

        stringBuilder.append("?p=" + this.currentPage);

        this.requestManga(stringBuilder.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.unbinder.unbind();
    }

    private void requestManga(String location) {
        try {
            RequestQueue queue = Volley.newRequestQueue(this.getContext());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    location,
                    null,
                    this,
                    this
                    );

            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray array = response.getJSONArray("manga");

            for (int i = 0; i < array.length(); i++) {
                JSONObject rawObject = array.getJSONObject(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
