package com.example.tubes_3.fragments.home;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tubes_3.R;
import com.example.tubes_3.fragments.adapters.HistoryAdapter;
import com.example.tubes_3.fragments.adapters.SwipeToDeleteCallback;
import com.example.tubes_3.messages.request.MangaHistoryRequestMessage;
import com.example.tubes_3.messages.response.MangaHistoryResponseMessage;
import com.example.tubes_3.model.HistoryDetail;
import com.example.tubes_3.model.HistoryRaw;
import com.example.tubes_3.presenters.HistoryPresenter;
import com.example.tubes_3.sharedPreference.MangaStorage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    @BindView(R.id.history_list) RecyclerView historyView;
    @BindView(R.id.history_progress_loader) ProgressBar loader;

    Unbinder unbinder;

    MangaStorage preferences;

    HistoryAdapter adapter;
    HistoryPresenter presenter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        this.unbinder = ButterKnife.bind(this, view);

        this.preferences = new MangaStorage(this.getContext());

        this.historyView.setItemAnimator(new LandingAnimator());

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

        List<HistoryRaw> historyRaws = this.preferences.getHistories();

        EventBus.getDefault().postSticky(new MangaHistoryRequestMessage(historyRaws));
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(MangaHistoryResponseMessage message) {
        this.loader.setVisibility(View.GONE);

        List<HistoryDetail> historyDetails = message.getHistoryDetails();

        this.presenter = new HistoryPresenter(historyDetails);
        this.adapter = new HistoryAdapter(this.getContext(), this.presenter);

        ItemTouchHelper helper = new ItemTouchHelper(new SwipeToDeleteCallback(this.adapter));
        helper.attachToRecyclerView(this.historyView);

        this.adapter.notifyDataSetChanged();
    }
}
