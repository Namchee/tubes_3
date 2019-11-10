package com.example.tubes_3.fragments.home;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tubes_3.R;
import com.example.tubes_3.fragments.adapters.MangaAdapter;
import com.example.tubes_3.model.Manga;
import com.example.tubes_3.model.URL_BASE;
import com.example.tubes_3.presenters.MangaPresenter;
import com.example.tubes_3.util.Parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    @BindView(R.id.manga_list) GridView mangaView;
    @BindView(R.id.search_bar) SearchView searchInput;
    @BindView(R.id.toolbar) Toolbar toolbar;

    int currentPage;
    Unbinder unbinder;

    MangaPresenter presenter;
    MangaAdapter adapter;

    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display, container, false);

        this.currentPage = 0;
        this.unbinder = ButterKnife.bind(this, view);

        this.presenter = new MangaPresenter();
        this.adapter = new MangaAdapter(this.getContext(), this.presenter);

        this.mangaView.setAdapter(this.adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.setToolbar();

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

                this.pushManga(rawObject);
            }

            this.adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void setToolbar() {
        this.getActivity().setActionBar(this.toolbar);
    }

    private void pushManga(JSONObject rawObject) {
        try {
            String id = rawObject.getString("i");
            String title = rawObject.getString("t");
            String imgUrl = "";

            if (rawObject.has("im") && rawObject.getString("im") != "null") {
                imgUrl = URL_BASE.IMAGE_SRC.getUrl() + rawObject.getString("im");
            }

            Date lastUpdated = null;

            if (rawObject.has("ld") && rawObject.getString("ld") != null) {
                lastUpdated = Parser.parseDate(rawObject.getString("ld"));
            }

            JSONArray categoriesRaw = rawObject.getJSONArray("c");
            List<String> categories = new ArrayList<>();

            for (int i = 0; i < categoriesRaw.length(); i++) {
                categories.add(categoriesRaw.getString(i));
            }

            String status = Parser.parseStatus(Integer.parseInt(rawObject.getString("s")));
            int hits = Integer.parseInt(rawObject.getString("h"));


            Manga manga = new Manga(id, title, imgUrl, lastUpdated, categories, status, hits);

            this.presenter.addManga(manga);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
