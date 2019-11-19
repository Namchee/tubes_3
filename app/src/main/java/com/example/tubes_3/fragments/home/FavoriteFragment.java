package com.example.tubes_3.fragments.home;


import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.adapters.MangaAdapter;
import com.example.tubes_3.messages.request.MangaFavoriteRequestMessage;
import com.example.tubes_3.messages.response.MangaFavoriteResponseMessage;
import com.example.tubes_3.messages.response.MangaListResponseMessage;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.presenters.MangaPresenter;
import com.example.tubes_3.sharedPreference.MangaStorage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    @BindView(R.id.favorites_list) RecyclerView favoritesView;
    @BindView(R.id.favorites_progress_loader) ProgressBar loader;
    @BindView(R.id.favorites_sum) TextView favoritesNum;

    private Unbinder unbinder;

    private MangaPresenter presenter;
    private MangaAdapter adapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        this.unbinder = ButterKnife.bind(this, view);

        int spanCount = 2;

        if (this.getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        }

        this.favoritesView.setLayoutManager(new GridLayoutManager(this.getContext(), spanCount));

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

        MangaStorage preferences = new MangaStorage(this.getContext());

        EventBus.getDefault().postSticky(new MangaFavoriteRequestMessage(preferences.getFavorites()));
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMangaFavoriteResponseMessage(MangaFavoriteResponseMessage mangaListResponseMessage) {
        this.loader.setVisibility(View.GONE);

        List<MangaRaw> mangaRawList = mangaListResponseMessage.getFavoritesList();

        this.presenter = new MangaPresenter(mangaRawList);
        this.adapter = new MangaAdapter(this.getContext(), this.presenter);

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(this.adapter);

        this.favoritesView.setAdapter(animationAdapter);

        LandingAnimator landingAnimator = new LandingAnimator();
        this.favoritesView.setItemAnimator(landingAnimator);

        this.setFavoritesNum(mangaRawList.size());
    }

    private void setFavoritesNum(int num) {
        this.favoritesNum.setText(num + " manga(s)");
    }
}
