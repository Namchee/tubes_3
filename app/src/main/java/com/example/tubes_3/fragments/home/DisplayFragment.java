package com.example.tubes_3.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.adapters.MangaAdapter;
import com.example.tubes_3.presenters.MangaPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {
    @BindView(R.id.manga_list) RecyclerView mangaView;
    @BindView(R.id.search_bar) SearchView searchInput;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.page_sum) TextView pageSum;
    // @BindView(R.id.progress_loader) ProgressBar progressBar;
    @BindView(R.id.sort_category) Spinner sortCategory;

    Unbinder unbinder;

    MangaPresenter presenter;
    MangaAdapter adapter;

    public DisplayFragment() {
        // Required empty public constructor
    }

    public DisplayFragment(MangaPresenter presenter) {
        // because bundle sucks
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_display, container, false);

        this.unbinder = ButterKnife.bind(this, view);

        this.mangaView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        this.setToolbar();

        this.adapter = new MangaAdapter(this.getContext(), this.presenter);

        this.mangaView.setAdapter(this.adapter);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.sort_criteria, android.R.layout.simple_spinner_dropdown_item);

        this.sortCategory.setAdapter(arrayAdapter);

        // this.sortCategory.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.unbinder.unbind();
    }

    private void setToolbar() {
        this.getActivity().setActionBar(this.toolbar);
    }

    /*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 1:
                this.adapter.fil
        }
    }
     */

    /*
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        this.sortCategory.setSelection(1);
    }
     */
}
