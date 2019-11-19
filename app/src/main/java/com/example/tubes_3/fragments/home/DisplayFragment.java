package com.example.tubes_3.fragments.home;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.adapters.MangaAdapter;
import com.example.tubes_3.interfaces.SearchableFragment;
import com.example.tubes_3.messages.RequestMessage;
import com.example.tubes_3.messages.response.MangaListResponseMessage;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.presenters.MangaPresenter;
import com.example.tubes_3.util.comparators.HitsSorter;
import com.example.tubes_3.util.comparators.LastUpdatedSorter;
import com.example.tubes_3.util.comparators.LexicographicSorter;
import com.example.tubes_3.util.comparators.ReverseLexicographicSorter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment implements Spinner.OnItemSelectedListener, SearchView.OnQueryTextListener, SearchableFragment, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.manga_list) RecyclerView mangaView;
    @BindView(R.id.search_bar) SearchView searchInput;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.page_sum) TextView pageSum;
    @BindView(R.id.display_progress_loader) ProgressBar loader;
    @BindView(R.id.sort_category) Spinner sortCategory;
    @BindView(R.id.swipe_to_refresh) SwipeRefreshLayout swipper;

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

        this.unbinder = ButterKnife.bind(this, view);

        int spanCount = 2;

        if (this.getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        }

        this.mangaView.setLayoutManager(new GridLayoutManager(this.getContext(), spanCount));
        this.setToolbar();

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.sort_criteria, android.R.layout.simple_spinner_dropdown_item);
        this.sortCategory.setAdapter(arrayAdapter);

        this.searchInput.setEnabled(false);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);

        this.loader.setVisibility(View.VISIBLE);

        EventBus.getDefault().postSticky(new RequestMessage());
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void setToolbar() {
        this.getActivity().setActionBar(this.toolbar);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Comparator<MangaRaw> comparator = null;
        switch (i) {
            case 0:
                comparator = new HitsSorter();
                break;
            case 1:
                comparator = new LastUpdatedSorter(); //TODO: fix this damn thing!
                break;
            case 2:
                comparator = new LexicographicSorter();
                break;
            case 3:
                comparator = new ReverseLexicographicSorter();
                break;
        }

        this.presenter.sort(comparator);

        this.adapter = new MangaAdapter(this.getContext(), this.presenter);
        this.mangaView.swapAdapter(this.adapter, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // DO NOTHING
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSum.setText(pageSize + " manga(s)");
    }

    @Subscribe
    public void handleMangaAllResponseMessage(MangaListResponseMessage mangaListResponseMessage) {
        this.loader.setVisibility(View.GONE);

        List<MangaRaw> raws = mangaListResponseMessage.getMangaRawList();

        Collections.sort(raws, new HitsSorter());

        this.presenter = new MangaPresenter(raws);

        this.adapter = new MangaAdapter(this.getContext(), this.presenter);
        this.adapter.setSearchableFragment(this);

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(this.adapter);

        this.mangaView.setAdapter(animationAdapter);

        LandingAnimator landingAnimator = new LandingAnimator();
        this.mangaView.setItemAnimator(landingAnimator);

        this.setPageSize(this.presenter.getSize());

        this.searchInput.setEnabled(true);
        this.searchInput.setOnQueryTextListener(this);

        this.sortCategory.setOnItemSelectedListener(this);

        this.swipper.setOnRefreshListener(this);

        this.adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.adapter.getFilter().filter(newText);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.adapter.getFilter().filter(query);

        this.searchInput.clearFocus();

        InputMethodManager imm = (InputMethodManager)this.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);

        return true;
    }

    @Override
    public void onRefresh() {
        this.loader.setVisibility(View.VISIBLE);

        EventBus.getDefault().postSticky(new RequestMessage());
    }
}
