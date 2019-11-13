package com.example.tubes_3.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.home.DisplayFragment;
import com.example.tubes_3.fragments.home.FavoriteFragment;
import com.example.tubes_3.fragments.home.HistoryFragment;
import com.example.tubes_3.interfaces.FragmentListener;
import com.example.tubes_3.messages.RequestMessage;
import com.example.tubes_3.messages.response.MangaAllResponseMessage;
import com.example.tubes_3.model.MangaRaw;
import com.example.tubes_3.presenters.MangaPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements FragmentListener, BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {
    FragmentManager homeFm;

    BottomNavigationView navigationView;

    DisplayFragment displayFragment;
    FavoriteFragment favoriteFragment;
    HistoryFragment historyFragment;

    MangaPresenter mangaPresenter;

    Unbinder unbinder;
    
    @BindView(R.id.home_progress_loader) ProgressBar loader;

    public static final int DISPLAY_ID = 1;
    public static final int FAVORITES_ID = 2;
    public static final int HISTORY_ID = 3;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.unbinder = ButterKnife.bind(this, view);

        this.homeFm = this.getChildFragmentManager();

        this.navigationView = view.findViewById(R.id.home_navigation);

        this.navigationView.setOnNavigationItemSelectedListener(this);
        this.navigationView.setOnNavigationItemReselectedListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        this.loader.setVisibility(View.VISIBLE);

        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        EventBus.getDefault().postSticky(new RequestMessage());
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }

    public void changePage(int id) {
        FragmentTransaction ft = this.homeFm.beginTransaction();

        ft.setCustomAnimations(R.anim.fragment_animation_in, R.anim.fragment_animation_out);

        this.hideAllFragment(ft);

        if (id == DISPLAY_ID) {
            if (!this.displayFragment.isAdded()) {
                ft.add(R.id.home_fragment_container, this.displayFragment, "display");
            } else {
                ft.show(this.displayFragment);
            }
        } else if (id == FAVORITES_ID) {
            if (!this.favoriteFragment.isAdded()) {
                ft.add(R.id.home_fragment_container, this.favoriteFragment, "favorites");
            } else {
                ft.show(this.favoriteFragment);
            }
        } else if (id == HISTORY_ID) {
            if (!this.historyFragment.isAdded()) {
                ft.add(R.id.home_fragment_container, this.historyFragment, "history");
            } else {
                ft.show(this.historyFragment);
            }
        }
        
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.favorite_menu:
                this.changePage(FAVORITES_ID);
                break;
            case R.id.display_menu:
                this.changePage(DISPLAY_ID);
                break;
            case R.id.history_menu:
                this.changePage(HISTORY_ID);
                break;
            default:
                System.exit(1);
        }

        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
        // Do nothing
    }

    @Override
    public void hideAllFragment(FragmentTransaction ft) {
        if (this.favoriteFragment.isVisible()) {
            ft.hide(this.favoriteFragment);
        }

        if (this.displayFragment.isVisible()) {
            ft.hide(this.displayFragment);
        }

        if (this.historyFragment.isVisible()) {
            ft.hide(this.historyFragment);
        }
    }

    @Subscribe
    public void handleMangaAllResponseMessage(MangaAllResponseMessage mangaAllResponseMessage) {
        this.loader.setVisibility(View.GONE);
        this.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        this.mangaPresenter = new MangaPresenter();
        List<MangaRaw> mangaRawList = mangaAllResponseMessage.getMangaRawList();

        for (MangaRaw mangaRaw: mangaRawList) {
            this.mangaPresenter.addManga(mangaRaw);
        }

        this.displayFragment = new DisplayFragment(mangaPresenter);
        this.favoriteFragment = new FavoriteFragment(mangaPresenter);
        this.historyFragment = new HistoryFragment(mangaPresenter);

        this.changePage(DISPLAY_ID);
    }
}
