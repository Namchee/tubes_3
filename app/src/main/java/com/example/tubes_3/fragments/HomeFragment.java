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

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.home.DisplayFragment;
import com.example.tubes_3.fragments.home.FavoriteFragment;
import com.example.tubes_3.fragments.home.HistoryFragment;
import com.example.tubes_3.interfaces.FragmentListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements FragmentListener, BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {
    FragmentManager homeFm;

    BottomNavigationView navigationView;

    DisplayFragment displayFragment;
    FavoriteFragment favoriteFragment;
    HistoryFragment historyFragment;

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

        this.homeFm = this.getChildFragmentManager();

        this.navigationView = view.findViewById(R.id.home_navigation);

        this.displayFragment = new DisplayFragment();
        this.favoriteFragment = new FavoriteFragment();
        this.historyFragment = new HistoryFragment();

        this.navigationView.setOnNavigationItemSelectedListener(this);
        this.navigationView.setOnNavigationItemReselectedListener(this);

        this.changePage(DISPLAY_ID);

        return view;
    }

    public void changePage(int id) {
        FragmentTransaction ft = this.homeFm.beginTransaction();

        ft.setCustomAnimations(R.anim.fragment_animation_in, R.anim.fragment_animation_out);

        this.hideAllFragment(ft);

        if (id == DISPLAY_ID) {
            if (!this.displayFragment.isAdded()) {
                ft.add(R.id.home_fragment_container, this.displayFragment, "display").addToBackStack("display");
            } else {
                ft.show(this.displayFragment);
            }
        } else if (id == FAVORITES_ID) {
            if (!this.favoriteFragment.isAdded()) {
                ft.add(R.id.home_fragment_container, this.favoriteFragment, "favorites").addToBackStack("favorites");
            } else {
                ft.show(this.favoriteFragment);
            }
        } else if (id == HISTORY_ID) {
            if (!this.historyFragment.isAdded()) {
                ft.add(R.id.home_fragment_container, this.historyFragment, "history").addToBackStack("history");
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
}
